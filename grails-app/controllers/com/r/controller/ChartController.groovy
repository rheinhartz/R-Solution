package com.r.controller

import grails.converters.JSON
class ChartController {

    def sessionFactory
    
    def index() { }
    
    def filter(){
        
    }
    
    def item_treemap(){
        
    }
    
    def item_sunburst(){
        
    }
    
    
    def jsonChartItem1(){
        def arr             = new ArrayList()
        def parent          = []
        def childrens       = []
        def grandchildrens  = []
        
        def sessionfactory = sessionFactory.currentSession
        def sql1 = ""
        def sql2 = ""
        def sql3 = ""
        
        sql1 = "select a.kind,SUM(a.stock) from item a group by a.kind order by a.name"
        def query1 = sessionfactory.createSQLQuery(sql1)
        query1.list().each { row1 ->
            childrens = []
            def kind = row1[0].toString()
            
            sql2 = "select a.brand,SUM(a.stock) from item a where a.kind like '$kind' group by a.brand order by a.brand"
            def query2 = sessionfactory.createSQLQuery(sql2)
            query2.list().each { row2 ->
                grandchildrens = []
                def brand = row2[0].toString()
                
                sql3 = "select a.name,SUM(a.stock) from item a where a.kind like '$kind' and a.brand like '$brand' and  a.brand is not null group by a.brand order by a.brand"
                def query3 = sessionfactory.createSQLQuery(sql3)
                query3.list().each { row3 ->
                    grandchildrens << [
                                         name       :row3[0].toString()
                                        ,size       :row3[1]
                                        ,value      :row3[1]
                                      ]
                }
                
                if( grandchildrens.size() > 0 ){
                    childrens << [
                                     name       :row2[0].toString()
                                    ,size       :row2[1]
                                    ,value      :row2[1]
                                    ,children   :grandchildrens
                                 ]
                }
                else{
                    childrens << [
                                     name       :row2[0].toString()
                                    ,size       :row2[1]
                                    ,value      :row2[1]
                                 ]
                }
                
            }
            
            if( childrens.size() > 0 ){
                parent << [
                            name           :row1[0].toString()
                           ,size           :row1[1]
                           ,value          :row1[1]
                           ,children       :childrens
                          ]
            }
            else{
                parent << [
                            name           :row1[0].toString()
                           ,size           :row1[1]
                           ,value          :row1[1]
                          ]
            }
            
        }
        
        def a = parent as JSON
       
        render """{ "name":"Item", "children":$a}"""
    }
    
    def jsonChartItem2(){
        def arr             = new ArrayList()
        def parent          = []
        def childrens       = []
        def grandchildrens  = []
        
        def sessionfactory = sessionFactory.currentSession
        def sqlL = ""
        def sql1 = ""
        def sql2 = ""
        def sql3 = ""
       
        def x = 0
        sqlL = ""
        sql1 = "SELECT location_id FROM location"
        def query1 = sessionfactory.createSQLQuery(sql1)
        
        query1.list().each { row1 ->
            if( x != 0 ){
                sqlL = sqlL + " UNION "
            }
            sqlL = sqlL + " SELECT $row1 AS location_id, SUM(i.stock_$row1), l.name FROM item i, location l WHERE l.location_id = $row1"
            x++
        }
        
        
        query1 = sessionfactory.createSQLQuery(sqlL)
        query1.list().each { row1 ->
            childrens = []
            def stock_num = row1[0].toString()
            
            sql2 = "SELECT a.kind, SUM(a.stock_$stock_num) FROM item a GROUP BY a.kind"
            def query2 = sessionfactory.createSQLQuery(sql2)
            query2.list().each { row2 ->
                grandchildrens = []
                def kind = row2[0].toString()
                
                sql3 = "SELECT a.name, a.stock_$stock_num FROM item a WHERE a.kind LIKE '$kind' ORDER BY a.name"
                def query3 = sessionfactory.createSQLQuery(sql3)
                query3.list().each { row3 ->
                    grandchildrens << [
                                         name       :row3[0].toString()
                                        ,size       :row3[1]
                                        ,value      :row3[1]
                                      ]
                }
                
                if( grandchildrens.size() > 0 ){
                    childrens << [
                                     name       :row2[0].toString()
                                    ,size       :row2[1]
                                    ,value      :row2[1]
                                    ,children   :grandchildrens
                                 ]
                }
                else{
                    childrens << [
                                     name       :row2[0].toString()
                                    ,size       :row2[1]
                                    ,value      :row2[1]
                                 ]
                }
            }
            
            if( childrens.size() > 0 ){
                parent << [
                            name           :row1[2].toString()
                           ,size           :row1[1]
                           ,value          :row1[1]
                           ,children       :childrens
                          ]
            }
            else{
                parent << [
                            name           :row1[0].toString()
                           ,size           :row1[1]
                           ,value          :row1[1]
                          ]
            }
        }
        
        def a = parent as JSON
       
        render """{ "name":"Item", "children":$a}"""
    }
}
