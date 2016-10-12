package com.r.service

import groovy.sql.Sql

class PhysicalService {

    def sessionFactory
    def grailsApplication
    
    def stockMethod(physicalInstance,params,sessionfactory) {
        
        def x = 0
        def pid     = physicalInstance.id
        def new_lid = physicalInstance.location_id
        def old_lid = 0

        def sql0 = "SELECT a.location_id FROM physical a WHERE a.physical_id = $pid"
        def query0 = sessionfactory.createSQLQuery(sql0)
        query0.list().each {row->
            old_lid = row
        }

        physicalInstance.items.each{
            def piid    = params.getAt("dummy_id_"+x)
            def itemidx = it.item_id//getAt("item["+x+"].item_id")
            def unitx   = it.unit//getAt("item["+x+"].unit")
            def qtyx    = it.qty//getAt("item["+x+"].qty")

            def stockvalueold       = 0                        
            def stockvaluecurrent   = 0
            def stockvaluenew       = 0
            def stockvalue          = 0
            
            def ovalue = 0
            def nvalue = 0

            //Getting old stock
            def sql1A = ""
            sql1A = "SELECT a.stock_$new_lid FROM item a WHERE a.item_id = $itemidx"
            def query1A = sessionfactory.createSQLQuery(sql1A)
            query1A.list().each {row->
                nvalue = row
            }
            
            def sql1B = ""
            sql1B = "SELECT a.stock_$old_lid FROM item a WHERE a.item_id = $itemidx"
            def query1B = sessionfactory.createSQLQuery(sql1B)
            query1B.list().each {row->
                ovalue = row
            }

            def sql2 = ""
            def oldqty1 = 1
            def oldpacking1 = 1
            sql2 = """
                    SELECT a.qty,b.packing
                    FROM physical_item a, item_pack b
                    WHERE 1=1
                    AND a.unit = b.unit
                    AND a.physical_item_id = $piid
                   """
            def query2 = sessionfactory.createSQLQuery(sql2)
            query2.list().each {row->
                oldpacking1  = row[1]
                oldqty1      = row[0] * oldpacking1
            }

            def sql3 = ""
            def oldqty2 = 1
            def oldpacking2 = 1
            sql3 = """
                      SELECT b.packing
                      FROM physical_item a, item_pack b
                      WHERE 1=1
                      AND a.unit = b.unit
                      AND a.physical_item_id = $piid
                   """
            def query3 = sessionfactory.createSQLQuery(sql3)
            query3.list().each {row->
                oldpacking2  = row
                oldqty2      = qtyx * row
            }

            def sql4 = ""
            def newpacking = 1
            sql4 = "SELECT a.packing FROM item_pack a WHERE a.item_id = $itemidx AND unit = '$unitx'"
            def query4 = sessionfactory.createSQLQuery(sql4)
            query4.list().each {row->
                 newpacking = row
            }

            stockvalueold = oldqty1
            stockvaluenew = oldqty2 * newpacking / oldpacking2

            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                println "CALL item_update_stock($itemidx,$old_lid,$new_lid,$stockvalueold,$stockvaluenew,$ovalue,$nvalue)"
                conn.execute("CALL item_update_stock($itemidx,$old_lid,$new_lid,$stockvalueold,$stockvaluenew,$ovalue,$nvalue)")
                conn.close()
            }catch(Exception e){println e.toString()}

            x++
        }
    }
    
    def checkDuplicate1(physicalInstance,params){
        def sessionfactory = sessionFactory.currentSession
        
        def qsize   = 0
        def cond    = false
        def message = ""
        def arr     = []
        def list    = []
        def locationid = physicalInstance.location_id
        
        physicalInstance.items.findAll{
            def itemid = it?.item_id

            def sql = """
                        SELECT a.doc_no,i.name
                        FROM physical a, physical_item b, item i
                        WHERE a.physical_id = b.physical_id
                        AND a.location_id = $locationid
                        AND b.item_id = $itemid
                        AND i.item_id = b.item_id
                        GROUP BY a.doc_no,i.name
                      """

            def query = sessionfactory.createSQLQuery(sql.toString())
            def queryList = query.list()
            qsize = queryList.size()

            if( qsize > 0 ){
                queryList.each{row->
                    arr << [ doc_no : row[0], item : row[1] ]
                }
            }

        }
        
        if( arr.size() > 0 ){
            cond    = true
            //message = "Duplicate Location and Item: Physical Doc No = "+arr[0].doc_no + "- Item = "+arr[0].item
            def x = 0
            arr.each{
                if( x != 0 ){
                    message = message+"<li>"
                }
                message = message + "Duplicate : Physical Doc No = "+it.doc_no + "- Item = "+it.item
                if( x != 0 ){
                    message = message+"</li>"
                }
                x++
            }
        }
        
        list << [
                     cond   : cond
                    ,message: message
                    ,arr    : arr
                ]

        return list
    }
    
}
