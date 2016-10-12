/* For Oracle */
/*
dataSource_mysql {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    url = "jdbc:mysql://localhost/RSOLUTION?useUnicode=yes&characterEncoding=UTF-8"
    username = "root"
    password = ""
}
dataSource {
    pooled = true
    dialect = "org.hibernate.dialect.Oracle10gDialect"
    driverClassName = 'oracle.jdbc.driver.OracleDriver'
    username = "RSOLUTION"
    password = "rsolution"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop"
            dialect = "org.hibernate.dialect.Oracle10gDialect"
            driverClassName = 'oracle.jdbc.driver.OracleDriver'
            url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=127.0.0.1)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SID=xe)))"
            username = "RSOLUTIONDEV"
            password = "rsolution"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            dialect = "org.hibernate.dialect.Oracle10gDialect"
            driverClassName = 'oracle.jdbc.driver.OracleDriver'
            url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=127.0.0.1)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SID=xe)))"
            username = "RSOLUTIONTEST"
            password = "rsolution"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            dialect = "org.hibernate.dialect.Oracle10gDialect"
            driverClassName = 'oracle.jdbc.driver.OracleDriver'
            url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=127.0.0.1)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SID=xe)))"
            username = "RSOLUTION"
            password = "rsolution"
        }
    }
    
}
*/

/* For MySql */

dataSource_oracle {
    //pooled = true
    //dialect = "org.hibernate.dialect.Oracle10gDialect"
    //driverClassName = 'oracle.jdbc.driver.OracleDriver'
    //username = "RSOLUTION"
    //password = "rsolution"
}
dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    url = "jdbc:mysql://localhost/RSOLUTION?useUnicode=yes&characterEncoding=UTF-8"
    username = "root"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost/RSOLUTIONDEV?useUnicode=yes&characterEncoding=UTF-8"
            username = "root"
            password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost/RSOLUTIONTEST?useUnicode=yes&characterEncoding=UTF-8"
            username = "root"
            password = ""
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost/RSOLUTION?useUnicode=yes&characterEncoding=UTF-8"
            username = "root"
            password = ""
        }
    }
}
