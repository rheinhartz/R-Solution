package com.r.service

import grails.converters.JSON

class MainService {

    def sessionFactory
    
    def docNoSequence(tablename){
        
        def sessionfactory = sessionFactory.currentSession
        def doc_no   = ""
        def module   = ""
        def sequence = "0000000001"
        def sql      = ""
        
        switch(tablename){
            case "physical"             : module = "PH-"; break;
            case "receiving"            : module = "RE-"; break;
            case "withdrawal"           : module = "WD-"; break;
            case "adjust"               : module = "AD-"; break;
            case "purchase_order"       : module = "PO-"; break;
            case "purchase"             : module = "PU-"; break;
            case "sales_order"          : module = "SO-"; break;
            case "sales"                : module = "SA-"; break;
            case "transfer"             : module = "TR-"; break;
        }
        
        doc_no = module + sequence
        
        sql = """
               SELECT  
                        1,
                        MAX( TRIM(LEADING '0' FROM (SUBSTRING(a.doc_no,4,length(a.doc_no)))) + 0 ) AS prevseqno
                       ,MAX( TRIM(LEADING '0' FROM (SUBSTRING(a.doc_no,4,length(a.doc_no)))) + 1 )AS nextseqno
                       ,MAX( length( TRIM(LEADING '0' FROM (SUBSTRING(a.doc_no,4,length(a.doc_no)))) + 0) ) AS lprevseqno
                       ,MAX( length( TRIM(LEADING '0' FROM (SUBSTRING(a.doc_no,4,length(a.doc_no)))) + 1) ) AS lnextseqno
                       ,MAX( SUBSTRING(a.doc_no,4,length(a.doc_no)) )as prevseq
                       ,MAX( CONCAT(
                        SUBSTRING(a.doc_no,4,length(a.doc_no) -3 - length( TRIM(LEADING '0' FROM (SUBSTRING(a.doc_no,4,length(a.doc_no)))) + 1) ),
                        TRIM(LEADING '0' FROM (SUBSTRING(a.doc_no,4,length(a.doc_no)))) + 1)
                        ) as nextseq
               FROM $tablename a
               WHERE 1=1
               GROUP BY 1 DESC
              """
        
        def query = sessionfactory.createSQLQuery(sql.toString())
        query.list().each { row->
            doc_no = module + row[6]
        }
        
        
        return doc_no
    }
    
    def selectDistinct(tablename, fieldname) {

        def list = []
        def sessionfactory = sessionFactory.currentSession
        def sql = ""
        
        sql = "select distinct(a.$fieldname) from $tablename a where 1=1"
            
        
        try{
            def query = sessionfactory.createSQLQuery(sql.toString())
            //println sql
            
            list << [fieldname: '']
            
            def queryList = query.list()
            queryList.collect {
                list << [fieldname: it.toString()]
            }
            
            /*
            def queryList = query.list()
            queryList.each { row->
                list.add row[0].toString()
            }
            */
            /*
            def queryList = query.list()
            queryList.collect {
                lstNcaList << [var1: it[0].toString(),var2: it[1].toString(),var3: it[2].toString()]
            }
            */
            
        }catch(Exception e){
            println sql
            println e.toString()
        }
        
        return list
    }
    
    def selectById(tablename, idname, fieldname, condition) {
       
        def list = []
        def sessionfactory = sessionFactory.currentSession
        def sql = ""
        
        sql = "select a.$idname, a.$fieldname from $tablename a where 1=1 $condition"
            
        
        try{
            def query = sessionfactory.createSQLQuery(sql.toString())
            //println sql
            
            //list << [fieldname: '', idname: '']
            
            def queryList = query.list()
            queryList.collect {
                list << [
                              idname    : it[0].toString()
                            , fieldname : it[1].toString()
                        ]
            }
            
        }catch(Exception e){
            println sql
            println e.toString()
        }
        
        return list
    }
    
    def getField(tablename, fieldname, condition) {
       
        def str = ""
        def sessionfactory = sessionFactory.currentSession
        def sql = ""
        
        sql = "select a.$fieldname from $tablename a where 1=1 $condition"
            
        try{
            def query = sessionfactory.createSQLQuery(sql.toString())
            
            def queryList = query.list()
            queryList.each { row->
                str = row.toString()
            }
            
        }catch(Exception e){
            println sql
            println e.toString()
        }
        
        return str
    }
    
    def listJson(tablename,listField,condition){
        
        def list = []
        def map = new HashMap()
        def sql = ""
        
        sql = "select a.* from rsolution.user a where 1=1 $condition"
            
        
        try{
            def query = sessionfactory.createSQLQuery(sql.toString())
            //query.setString('cond1', cond1)

            def queryList = query.list()
            queryList.collect {
                list << [
                         // var1: it[0].toString()
                         //,var2: it[1].toString()
                         //,var3: it[2].toString()
                        ]
            }
        }catch(Exception e){
            println sql
        }
        

        def jsonData = [total: list.size(), rows: list]

        render jsonData as JSON

    }
    
    def listTable(tablename,listField,condition){
        
        def sessionfactory = sessionFactory.currentSession
        
        def list = []
        def map = new HashMap()
        def sql = ""
        
        sql = " SELECT "
        for( i in 0..(listField.size()-1) ){
            if( i > 0 ){
                sql += ","
            }
            
            sql += " a."+listField[i]
        }
        sql +=  " FROM $tablename a where 1=1 $condition"
            
        
        try{
            def query = sessionfactory.createSQLQuery(sql)
            //query.setString('cond1', cond1)

            def queryList = query.list()
            queryList.collect {
                
                map = new HashMap()
                
                for( i in 0..(listField.size()-1) ){
                    map.put( listField[i] , it[i] )
                    
                    if( listField[i].equals("location_id") ){
                        def idx = it[i]
                        map.put( "location" , getField("location","name"," AND a.location_id = $idx") )    
                    }
                    if( listField[i].equals("item_id") ){
                        def idx = it[i]
                        map.put( "item" , getField("item","name"," AND a.item_id = $idx") )    
                    }
                }
                //map.put( "fname", it[0].toString() )
                //map.put( "mname", it[1].toString() )
                //map.put( "lname", it[2].toString() )
                
                list << map
            }
        }catch(Exception e){
            println e.toString()
            println sql
        }
        

       return list

    }
    
    def getControllerNames(){
        
        def list = []
        
        //def clist = grailsApplication.controllerClasses.sort { it.fullName }
        //clist.each{
          //  list.add( it.logicalPropertyName )
        //}
        
        /*
        list.add( "home" )
        list.add( "user" )
        list.add( "contact" )
        list.add( "author" )
        list.sort()
        */
       
        list << [ fieldname: "*" ]
        list << [ fieldname: "author" ]
        list << [ fieldname: "contact" ]
        list << [ fieldname: "home" ]
        list << [ fieldname: "user" ]
        list << [ fieldname: "item" ]
        list << [ fieldname: "location" ]
        list << [ fieldname: "physical" ]
        //list.sort()
        
        return list
    }
    
    def getControllerActions(){
        
        def list = []
        
        list << [ fieldname: "*" ]
        list << [ fieldname: "add" ]
        list << [ fieldname: "edit" ]
        list << [ fieldname: "delete" ]
        list << [ fieldname: "list" ]
        list << [ fieldname: "view" ]
        list << [ fieldname: "save" ]
        list << [ fieldname: "show" ]
        //list.sort()
        
        return list
    }
    
}

