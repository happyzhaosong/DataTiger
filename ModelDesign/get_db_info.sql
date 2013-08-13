12 hours: page url = 148397, product item = 13324

15 hours: download_task : 168180, data_taobao_jie : 34545         

=====================================================================
 
SELECT COUNT(*) FROM download_task

SELECT COUNT(page_url) FROM download_task

SELECT COUNT(DISTINCT(page_url)) FROM download_task

SELECT COUNT(*) FROM download_task WHERE apply_time = ""

SELECT COUNT(*) FROM data_tao_bao_jie

SELECT COUNT(shop_taobao_url) FROM data_tao_bao_jie

SELECT COUNT(DISTINCT(shop_taobao_url)) FROM data_tao_bao_jie

SELECT COUNT(shop_rate_url) FROM data_tao_bao_jie

SELECT COUNT(DISTINCT(shop_rate_url)) FROM data_tao_bao_jie

SELECT * FROM data_tao_bao_jie WHERE shop_taobao_url = ''

SELECT COUNT(*) FROM data_tao_bao_jie WHERE shop_name IS NULL

SELECT COUNT(*) FROM data_tao_bao_jie WHERE shop_taobao_url IS NULL

SELECT * FROM data_tao_bao_jie WHERE shop_name IS NULL;

SELECT * FROM data_tao_bao_jie WHERE shop_taobao_url IS NULL

SELECT * FROM data_tao_bao_jie ORDER BY shop_phone_number DESC