dataSource {
	pooled = true
	jmxExport = true
	driverClassName = 'org.h2.Driver'
	username = 'sa'
	password = ''
	dbCreate = 'create-drop'
	url = 'jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = false
	cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory'
	singleSession = true
	flush.mode = 'manual'
	format_sql = true
	use_sql_comments = true
}
