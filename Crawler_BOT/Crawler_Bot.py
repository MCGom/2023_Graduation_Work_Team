

import time
import schedule
import pymysql
import Crawler_Kakao
import Crawler_Lezin
import Crawler_Naver


def Call_Crawlers() :
    print("크롤링 실행중")
    Crawler_Kakao.main()
    time.sleep(5)
    Crawler_Lezin.main()
    time.sleep(5)
    Crawler_Naver.main()

def main():
    schedule.every().day.at("00:10").do(Call_Crawlers)

    while True:
        schedule.run_pending()
        time.sleep(10)
        #db 연결
        conn = pymysql.connect(host='localhost', user='root', password='1234', db='toon_plus', charset='utf8')
        #db 커서 설정
        curs = conn.cursor()

        select_query = "SELECT Toon_category FROM toon_table ORDER BY Toon_idx"  
        curs.execute(select_query)
        sql_i = 0
        for row in curs.fetchall():
            tags = str(row[0])
            changed_tags = ""
            if "액션" in tags or "무협" in tags:
                changed_tags = changed_tags + "#액션"
            if "판타지" in tags or "이종족" in tags or "이능력" in tags or "sf" in tags or "로판" in tags or "마법" in tags or "시대극" in tags :
                changed_tags = changed_tags + "#판타지"
            if "로맨스" in tags or "연인" in tags or "BL" in tags or "백합" in tags or "연애" in tags or "러브" in tags  or "로판" in tags :
                changed_tags = changed_tags + "#로맨스"
            if "드라마" in tags or "스포츠" in tags or "성장" in tags or "연예계" in tags  or "감성" in tags :
                changed_tags = changed_tags + "#드라마"
            if "학원" in tags or "동아리" in tags or "고등학교" in tags or "학생" in tags or "캠퍼스" in tags:
                changed_tags = changed_tags + "#학원"
            if "코미디" in tags or "코믹" in tags or "개그" in tags or "병맛" in tags or "일상" in tags or "유쾌함" in tags or "동물" in tags:
                changed_tags = changed_tags + "#코미디/일상"
            if "공포" in tags or "스릴러" in tags or "범죄" in tags or "미스터리" in tags or "호러" in tags or "괴담" in tags  or "서스펜스" in tags:
                changed_tags = changed_tags + "#스릴러"
            
            curs.execute("update toon_table set Toon_category = %s where Toon_idx = %s" , (changed_tags, sql_i))
            sql_i = sql_i + 1
                
        curs.execute("update toon_review set Toon_exist_for_review = 0")
        conn.commit()
        
        toon_review_query = "select Toon_name from toon_table"
        curs.execute(toon_review_query)
        toon_review_result = curs.fetchall()
        for row in toon_review_result:    
            
            curs.execute("select Toon_name from toon_review where Toon_name = %s", row[0])
            tmp_result = curs.fetchall()
            
            if not tmp_result: 
                curs.execute("insert into toon_review (Toon_name, Toon_exist_for_review) values (%s, %s)", (row[0], 1))
            else:
                curs.execute("update toon_review set Toon_exist_for_review = %s where Toon_name = %s" , (1, row[0]))
        
        curs.execute("delete from toon_review where Toon_exist_for_review = 0")
        
        conn.commit()
        conn.close()
        time.sleep(30)
        print("크롤링 대기중")
            
            
            


if __name__ == '__main__':
    main()