
import json
import requests
import time
from bs4 import BeautifulSoup
import pymysql
import datetime

def main():
    def get_last_toon_idx(cursor):
        try:
            cursor.execute("SELECT MAX(Toon_idx) AS max_toon_idx FROM toon_table")
            result = cursor.fetchone()
            max_toon_idx = result['max_toon_idx']
            if max_toon_idx is not None:
                return max_toon_idx
            else:
                return 0
        except pymysql.Error as error:
            print(f"MySQL 오류: {error}")
            return 0

    def epi_info(series_id, state, Toon_idx):
        epi_url = 'https://webtoon.kakao.com/content/text/' + str(series_id)
        epi_res = requests.get(epi_url)
        today_weekday = datetime.datetime.now().strftime("%a").lower()

        soup = BeautifulSoup(epi_res.content, 'html.parser')
        result = soup.select_one('#__NEXT_DATA__').get_text()
        json_data = json.loads(result)

        Toon_name = json_data['props']['initialState']['content']['contentMap'][str(series_id)]['title']
        Toon_category_list = '#' + json_data['props']['initialState']['content']['contentMap'][str(series_id)]['genre']
        Toon_category = '#'.join([gerne for gerne in Toon_category_list.split('/')])
        Toon_link = f"https://webtoon.kakao.com/content/text/{series_id}"
        Toon_update = 1 if day == today_weekday else 0
        Toon_platform = "3"  # 플랫폼 정보 (카카오 웹툰을 임의로 3으로 설정)
        Toon_likes = "0" # 0으로 초기화
        Toon_imagelink = soup.find('meta', property='og:image')['content']
        Toon_newupdate = "0"  # 0으로 초기화
        Toon_exist = "0" #0으로 초기화
        
        return [
            Toon_idx,
            Toon_name,
            Toon_category,
            Toon_link,
            Toon_update,
            Toon_platform,
            Toon_likes,
            Toon_imagelink,
            Toon_newupdate,
            Toon_exist
        ]

    def get_series_by_day(day):
        # 요일별 연재작 수집
        url = f'https://gateway-kw.kakao.com/section/v1/timetables/days?placement=timetable_{day}'

        res = requests.get(url)
        contents = res.json()['data'][0]['cardGroups'][0]['cards']

        error_page = []
        
        try:
            connection = pymysql.connect(host="localhost", user="root", password="1234", db="toon_plus", charset='utf8', cursorclass=pymysql.cursors.DictCursor)
            cursor = connection.cursor()
        
            # 기존 데이터의 개수를 가져와서 Toon_idx 초기값 설정
            last_toon_idx = get_last_toon_idx(cursor)
            Toon_idx = last_toon_idx + 1
        
            for series in contents:
                series_id = series['content']['id']
                is_adult = series['content']['adult']  # adult 속성 확인
        
                if not is_adult:  # adult가 false일 때만 데이터 수집
                    try:
                        data = epi_info(series_id, day, Toon_idx)  # Toon_idx를 epi_info 함수로 전달
                        insert_or_update_mysql(data, cursor)
                        Toon_idx += 1  # Toon_idx 증가
                        time.sleep(3)
        
                    except Exception as e:
                        print(f"에러 발생: {e}")
                        print(series_id)
                        error_page.append(series_id)
        
            print(f"{day}에 대한 에러 목록: {error_page}")
        
        except pymysql.Error as error:
            print(f"MySQL 연결 오류: {error}")
        finally:
            if 'connection' in locals():
                cursor.close()
                connection.close()

    def insert_or_update_mysql(data, cursor):
        host = "localhost"
        user = "root"
        password = "1234"
        database = "toon_plus"
        
        try:
            connection = pymysql.connect(host=host, user=user, password=password, db=database, charset='utf8', cursorclass=pymysql.cursors.DictCursor)
            cursor = connection.cursor()
            
            # 데이터베이스에서 해당 Toon_name을 검색하여 중복을 확인합니다.
            select_query = "SELECT * FROM toon_table WHERE Toon_name = %s"
            cursor.execute(select_query, data[1])  # data[1]은 Toon_name입니다.
            existing_data = cursor.fetchone()

            if existing_data is None:
                # Toon_name이 존재하지 않는 경우에만 데이터베이스에 데이터를 삽입합니다.
                insert_query = "INSERT INTO toon_table (Toon_idx, Toon_name, Toon_category, Toon_link, Toon_update, Toon_platform, Toon_likes, Toon_imagelink, Toon_newupdate, Toon_exist) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, 1)"  # Toon_exist를 1로 하드코딩
                cursor.execute(insert_query, data[:-1])  # 마지막 요소(Toon_exist)를 제외한 데이터 전달
                connection.commit()
                print("데이터가 성공적으로 삽입되었습니다.")
            else:
                # 이미 존재하는 경우에는 업데이트합니다.
                update_query = "UPDATE toon_table SET Toon_category = %s, Toon_link = %s, Toon_update = %s, Toon_platform = %s, Toon_imagelink = %s, Toon_newupdate = %s, Toon_exist = 1 WHERE Toon_name = %s"  # Toon_exist를 1로 하드코딩
                cursor.execute(update_query, (data[2], data[3], data[4], data[5], data[7], data[8], data[1]))  # 마지막 요소(Toon_exist)를 제외한 데이터 전달
                connection.commit()
                print(f"{data[1]} 데이터가 성공적으로 업데이트되었습니다.")

        except pymysql.Error as error:
            print(f"MySQL 오류: {error}")
        finally:
            if connection:
                connection.close()


    def update_toon_newupdate(cursor):
        try:
            connection = pymysql.connect(host="localhost", user="root", password="1234", db="toon_plus", charset='utf8', cursorclass=pymysql.cursors.DictCursor)
            cursor = connection.cursor()
            
            # DB에서 모든 웹툰 데이터를 가져옵니다.
            cursor.execute("SELECT Toon_idx, Toon_name FROM toon_table where Toon_platform = 3")
            webtoons = cursor.fetchall()

            # 웹툰 이름을 가져와서 DB에 저장된 Toon_name과 비교하여 Toon_newupdate를 업데이트합니다.
            for webtoon in webtoons:
                Toon_idx = webtoon['Toon_idx']
                Toon_name = webtoon['Toon_name']
                new_toon_url = f'https://gateway-kw.kakao.com/section/v1/timetables/days?placement=timetable_new'
                
                res = requests.get(new_toon_url)
                soup = BeautifulSoup(res.content, 'html.parser')
                
                # 웹툰 이름이 웹툰 목록 페이지에 있는지 확인합니다.
                if Toon_name in str(soup):
                    # 웹툰이 목록에 있는 경우 Toon_newupdate를 1로 업데이트합니다.
                    update_query = "UPDATE toon_table SET Toon_newupdate = 1 WHERE Toon_idx = %s"
                    cursor.execute(update_query, Toon_idx)
                    connection.commit()
                    print(f"{Toon_name}의 Toon_newupdate가 업데이트되었습니다.")
                else:
                    print(f"{Toon_name}은(는) 목록에 없습니다.")


        except pymysql.Error as error:
            print(f"MySQL 오류: {error}")
        finally:
            if connection:
                connection.close()

    def delete_toon_with_platform_and_exist(cursor):
        try:
            connection = pymysql.connect(host="localhost", user="root", password="1234", db="toon_plus", charset='utf8', cursorclass=pymysql.cursors.DictCursor)
            cursor = connection.cursor()

            # Toon_platform이 3이고 Toon_exist가 0인 데이터 삭제
            delete_query = "DELETE FROM toon_table WHERE Toon_platform = 3 AND Toon_exist = 0"
            cursor.execute(delete_query)
            connection.commit()
            print("Toon_platform이 3이고 Toon_exist가 0인 데이터가 삭제되었습니다.")

        except pymysql.Error as error:
            print(f"MySQL 오류: {error}")
        finally:
            if connection:
                connection.close()

    try:
        connection = pymysql.connect(host="localhost", user="root", password="1234", db="toon_plus", charset='utf8', cursorclass=pymysql.cursors.DictCursor)
        cursor = connection.cursor()
        cursor.execute("UPDATE toon_table SET Toon_exist = 0 WHERE Toon_platform = 3")
        connection.commit()

        today_weekday = datetime.datetime.now().strftime("%a").lower()

        # 월요일부터 일요일까지 반복하여 웹툰 정보 수집
        days_of_week = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun']
        # 현재 요일을 가장 마지막으로 이동
        if today_weekday in days_of_week:
            days_of_week.remove(today_weekday)
            days_of_week.append(today_weekday)

        for day in days_of_week:
            get_series_by_day(day)

        # Toon_newupdate 값을 업데이트합니다.
        update_toon_newupdate(cursor)

        # Toon_platform이 3이고 Toon_exist가 0인 데이터 삭제 및 인덱스 재조정
        delete_toon_with_platform_and_exist(cursor)

    except pymysql.Error as error:
        print(f"MySQL 연결 오류: {error}")
    finally:
        if 'connection' in locals():
            cursor.close()
            connection.close()
if __name__ == '__main__':
    main()