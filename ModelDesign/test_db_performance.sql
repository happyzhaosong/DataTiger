SELECT * FROM download_task WHERE site_id = 3 AND useless_content_page = 0 AND apply_time = '' LIMIT 0,500

SELECT * FROM download_task WHERE site_id = 3 AND useless_content_page = 0 AND apply_time = '' AND task_level > 0 

SELECT * FROM download_task WHERE useless_content_page = 0 AND apply_time = '' ORDER BY task_level DESC, if_content_page DESC, id ASC

 SELECT apply_time,error_message,id,if_content_page,if_site_top_url,if_test,in_db_time,page_data,page_url,parsed_out_xml,run_time,site_id,task_level,task_run_delta_time,thread_table_id,useless_content_page FROM download_task   WHERE  site_id  = 3 AND useless_content_page = 0 AND apply_time = ''  LIMIT 0, 100 
  
data_taobao_jie 21:30 ALL 206204, AREA !='' 123392
 
download_task 21:30 ALL 645831, applied task 238814

applying_task

SELECT applying_task FROM download_thread_apply_task_status


UPDATE download_thread_apply_task_status SET applying_task = 0

SELECT COUNT(id) FROM download_task WHERE task_level = 5

SELECT COUNT(DISTINCT(download_task_page_url)) FROM data_tao_bao_jie

download_task_id 

SELECT COUNT(*) FROM data_tao_bao_jie WHERE download_task_level = 5

download_task_level
 
 
