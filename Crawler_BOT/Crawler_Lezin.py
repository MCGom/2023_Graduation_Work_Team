

#셀레니움 사용을 위한 임포트
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
#웹 드라이버 사용을 위한 임포트
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.common.exceptions import NoSuchElementException
from collections import OrderedDict
from datetime import datetime
#sleep 사용을 위한 임포트
import time
#mysql 사용을 위한 임포트
import pymysql

def main():
    #요일 설정을 위한 코드
    if(datetime.today().weekday() != 6):
        today = datetime.today().weekday() + 1
    else:
        today = 0

    #db 연결
    conn = pymysql.connect(host='localhost', user='root', password='1234', db='toon_plus', charset='utf8')
    #db 커서 설정
    curs = conn.cursor()


    #테이블 비우기
    curs.execute("update toon_table set Toon_exist = 0 where Toon_platform = 2")
    conn.commit()

    #크롤링 페이지 설정
    option = Options()
    option.add_argument("disable-infobars")
    option.add_argument("disable-extensions")
    # option.add_argument("start-maximized")
    #option.add_argument('disable-gpu')
    #option.add_argument('headless')
    s = Service()
    #웹툰 정보 수집을 위한 드라이버
    browser = webdriver.Chrome(service=s, options=option)
    wait = WebDriverWait(browser, 10)
    #웹툰 태그 수집을 위한 드라이버
    specific_browser = webdriver.Chrome(service=s, options=option)
    wait = WebDriverWait(specific_browser, 10)


    webtoon_name_for_crawl = []
    webtoon_img_for_crawl = []
    webtoon_link_for_crawl = []
    webtoon_update = []

    page_link = "https://www.lezhin.com/ko/scheduled?day=" + str(today)

    #웹툰 정보 수집을 위한 페이지 열기
    browser.get(page_link)
    browser.implicitly_wait(10)
    #바디 불러오기
    actions = browser.find_element(By.CSS_SELECTOR, 'body')

    #스크롤 내리는 부분
    for j in range(10):
        time.sleep(2)
        actions.send_keys(Keys.PAGE_DOWN)
    data_link = "scheduled-day-" + str(today)
    webtoon_data = browser.find_element(By.ID, data_link)

    webtoon_today_data = webtoon_data.find_elements(By.CLASS_NAME, "lzComic__item")
    for element in webtoon_today_data:
        
        #웹툰 이름 수집
        webtoon_name = element.find_element(By.CLASS_NAME, "lzComic__title")
        #웹툰 이미지 수집
        webtoon_img = element.find_element(By.TAG_NAME, "img")
        #웹툰 링크 수집
        webtoon_link = element.find_element(By.CLASS_NAME, "lzComic__link")

        webtoon_name_for_crawl.append(str(webtoon_name.text))
        webtoon_img_for_crawl.append(str(webtoon_img.get_attribute('srcset')))    
        webtoon_link_for_crawl.append(str(webtoon_link.get_attribute('href')))
        try:
            updated = element.find_element(By.CLASS_NAME, 'lzIcon__up')
            webtoon_update.append(1)
        except NoSuchElementException:
            webtoon_update.append(0)
        
    page_link = "https://www.lezhin.com/ko/scheduled?day=n"

    #웹툰 정보 수집을 위한 페이지 열기
    browser.get(page_link)
    browser.implicitly_wait(10)
    #바디 불러오기
    actions = browser.find_element(By.CSS_SELECTOR, 'body')

    #스크롤 내리는 부분
    for j in range(10):
        time.sleep(2)
        actions.send_keys(Keys.PAGE_DOWN)
    data_link = "scheduled-day-n"
    webtoon_data = browser.find_element(By.ID, data_link)

    webtoon_tdays_data = webtoon_data.find_elements(By.CLASS_NAME, "lzComic__item")
    for element in webtoon_tdays_data:
        
        #웹툰 이름 수집
        webtoon_name = element.find_element(By.CLASS_NAME, "lzComic__title")
        #웹툰 이미지 수집
        webtoon_img = element.find_element(By.TAG_NAME, "img")
        #웹툰 링크 수집
        webtoon_link = element.find_element(By.CLASS_NAME, "lzComic__link")

        webtoon_name_for_crawl.append(str(webtoon_name.text))
        webtoon_img_for_crawl.append(str(webtoon_img.get_attribute('srcset')))    
        webtoon_link_for_crawl.append(str(webtoon_link.get_attribute('href')))
        try:
            updated = element.find_element(By.CLASS_NAME, 'lzIcon__up')
            webtoon_update.append(1)
        except NoSuchElementException:
            webtoon_update.append(0)
            
    for i in range(0, 7):
        if(i != today):
            page_link = "https://www.lezhin.com/ko/scheduled?day=" + str(i)

            #웹툰 정보 수집을 위한 페이지 열기
            browser.get(page_link)
            browser.implicitly_wait(10)
            #바디 불러오기
            actions = browser.find_element(By.CSS_SELECTOR, 'body')

            #스크롤 내리는 부분
            for j in range(10):
                time.sleep(2)
                actions.send_keys(Keys.PAGE_DOWN)
            data_link = "scheduled-day-" + str(i)
            webtoon_data = browser.find_element(By.ID, data_link)
            
            #웹툰 이름 수집
            webtoon_name = webtoon_data.find_elements(By.CLASS_NAME, "lzComic__title")
            #웹툰 이미지 수집
            webtoon_img = webtoon_data.find_elements(By.TAG_NAME, "img")
            #웹툰 링크 수집
            webtoon_link = webtoon_data.find_elements(By.CLASS_NAME, "lzComic__link")
            
            for name in webtoon_name:
                webtoon_name_for_crawl.append(str(name.text))
                webtoon_update.append(0)
            for img in webtoon_img:
                webtoon_img_for_crawl.append(str(img.get_attribute('srcset')))    
            for link in webtoon_link:
                webtoon_link_for_crawl.append(str(link.get_attribute('href')))


    webtoon_name_for_db = list(OrderedDict.fromkeys(webtoon_name_for_crawl))
    webtoon_img_for_db = list(OrderedDict.fromkeys(webtoon_img_for_crawl))
    webtoon_link_for_db = list(OrderedDict.fromkeys(webtoon_link_for_crawl))
        
    webtoon_tag_for_db = ["" for i in range(len(webtoon_link_for_db)) ]

    tag_i = 0

    for element in webtoon_link_for_db:
        specific_browser.get(element)
        specific_browser.implicitly_wait(4)
        time.sleep(2)
        webtoon_tag = specific_browser.execute_script("return document.querySelector('.comicInfoExtend__tags').innerText")
        webtoon_tag_for_db[tag_i] = webtoon_tag_for_db[tag_i] + webtoon_tag
        tag_i = tag_i + 1
        
    curs.execute("select max(Toon_idx) from toon_table")
    sql_i = curs.fetchone()[0] + 1

    webtoon_i = 0

    for idx in webtoon_name_for_db:
        curs.execute("select Toon_name from toon_table where Toon_name = %s", idx)
        tmp_result = curs.fetchall()
        if not tmp_result: 
            curs.execute("insert into toon_table (Toon_idx, Toon_name, Toon_imagelink, Toon_link, Toon_category, Toon_update, Toon_platform, Toon_newupdate, Toon_exist, Toon_likes) values (%s, %s, %s, %s, %s, %s, %s, %s, %s, 0)", (sql_i, webtoon_name_for_db[webtoon_i], webtoon_img_for_db[webtoon_i], webtoon_link_for_db[webtoon_i], webtoon_tag_for_db[webtoon_i], webtoon_update[webtoon_i], 2, 0, 1))
            sql_i = sql_i + 1
            print('data_inserted')
        else:
            curs.execute("update toon_table set Toon_name = %s, Toon_imagelink = %s, Toon_link = %s, Toon_category = %s, Toon_update = %s, Toon_platform = %s, Toon_newupdate = %s, Toon_exist =%s where Toon_name = %s", (webtoon_name_for_db[webtoon_i], webtoon_img_for_db[webtoon_i], webtoon_link_for_db[webtoon_i], webtoon_tag_for_db[webtoon_i], webtoon_update[webtoon_i], 2, 0, 1, idx))
            print('data_updated')
        webtoon_i = webtoon_i + 1
        
    conn.commit()

    browser.get("https://www.lezhin.com/ko/scheduled/new-released?t=all")
    browser.implicitly_wait(10)

    body = browser.find_element(By.CSS_SELECTOR, 'body')
    new_actions = browser.find_element(By.ID, "new-scheduled")

    for j in range(30):
        time.sleep(2)
        body.send_keys(Keys.PAGE_DOWN)


    new_webtoon_name = new_actions.find_elements(By.CLASS_NAME, "lzComic__title")

    new_webtoon_name_for_db = []

    for element in new_webtoon_name:
        new_webtoon_name_for_db.append(str(element.text))

    for new_webtoon in new_webtoon_name_for_db:
        curs.execute("UPDATE toon_table SET Toon_newupdate = 1 WHERE Toon_name = %s", new_webtoon)

    curs.execute("delete from toon_table where Toon_exist = 0 and Toon_platform = 2")

    conn.commit()
    conn.close()
if __name__ == '__main__':
    main()