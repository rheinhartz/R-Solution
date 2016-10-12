package com.r.controller

import grails.converters.JSON
class TestController {

    def sessionFactory
    
    def index() { 
         
    }
    
    
    def jqplot(){
        
    }
    
    def jqplot_barh(){
        
    }
    
    def jqplot_barv(){
        
    }
    
    def jqplot_donut(){
        
    }
    
    def jqplot_pie(){
        
    }
    
    def jqplot_x1(){
        
    }
    
    def jqplot_x2(){
        
    }
    
    def jqplot_z1(){
        
    }
    
    def jqplot_z2(){
        
    }
    
    
    def d3(){
        
    }
    
    def d3_choropleth(){
        
    }
    
    def d3_treemap(){
        
    }
    
    def d3_sunburst(){
        
    }
    
    def dataTest(){
        
        def lst = []
        
        def sessionfactory = sessionFactory.currentSession
        def sql1 = ""
        def sql2 = ""
        
        sql1 = "select a.module,a.title,a.code from client.setup a where 1=1 order by a.module,a.title"
            
        def tempagy = ""
        def tempdpt = ""
        def str = ""
        
        try{
            str = """{"name": "flare","children": ["""
            
            def query1 = sessionfactory.createSQLQuery(sql1)
            def plist = query1.list()
            def j = 1;
            plist.each { row1 ->
                
                
                if( tempdpt.equalsIgnoreCase(row1[0]) ){

                }else{
                    def a = row1[0]
                    def cond = row1[0]
                    sql2 = "select a.module,a.title,a.code from client.setup a where a.module like '%$cond%' order by a.module,a.title"
                    
                    str += """{"name": "$a","children": ["""
                    def query2 = sessionfactory.createSQLQuery(sql2)
                    def qlist = query2.list()
                    def i = 1;
                    
                    qlist.each { row2 ->
                        
                        def b = row2[1]
                        def c = row2[2]
                        str += """{"name" : "$b", "size": $c}"""
                        
                        
                        if( i == qlist.size() ){
                            
                        }else{
                            i++
                            str += ","
                        }
                        
                        
                    }

                    str += """]}"""
                    
                    println j + " - " + plist.size()
                    if( j == plist.size() ){
                            
                    }else{
                        str += ","
                    }
                    
                    tempdpt = row1[0] 
                }
                
               j++ 
               
            }
            
           
            str +="""]}"""
            
        }catch(Exception e){
            println sql
            println e.toString()
        }
        

        //def jsonData = [total: lstNcaList.size(), rows: lstNcaList]

        render str
        
    }
    
    def dataTest1(){
        
        def lst = []
        
        def sessionfactory = sessionFactory.currentSession
        def sql1 = ""
        def sql2 = ""
        
        sql1 = "select a.module,a.title,a.code from client.setup a where 1=1 order by a.module,a.title"
            
        
        def tempagy = ""
        def tempdpt = ""
        def str = ""
        
        try{
            str = """
                    {
                        "name": "flare",
                        "children": [
                  """
            
            def query1 = sessionfactory.createSQLQuery(sql1)
            query1.list().each { row1 ->
                
                if( tempdpt.equalsIgnoreCase(row1[0]) ){
                    str += """
                            
                           """
                    //str += sql2 +"" 
                }else{
                    def a = row1[0]
                    def cond = row1[0]
                    sql2 = "select a.module,a.title,a.code from client.setup a where a.module like '%$cond%' order by a.module,a.title"
                    
                    str += """
                        {
                        "name": "$a",
                        "children": [
                       """
                    def query2 = sessionfactory.createSQLQuery(sql2)
                    def qlist = query2.list()
                    def i = 1;
                    
                    qlist.each { row2 ->
                        
                        def b = row2[1]
                        def c = row2[2]
                        str += """
                                {"name" : "$b", "size": $c}
                               """
                        
                        println i +"=="+ qlist.size()
                        if( i == qlist.size() ){
                            
                        }else{
                            i++
                            str += ","
                        }
                    }
                    //str += tempdpt + " - " +row1[0] + ""
                    //str += ""
                    str += """
                        ]
                        },
                       """
                    
                    tempdpt = row1[0] 
                }
                
                if( tempdpt.equalsIgnoreCase(row1[0]) ){
                        
                }else{
                    
                }
                
                
                
                /*
                def a = row1[0]
                str += """
                        "name": "$a",
                        "children": [{
                       """
                
                query2.list().each { row2 ->
                       
                    def b = row2[1]
                    def c = row2[2]
                    str += """  {"name": "$b", "size": $c}, """
                }
                str +=  "}"
                */
                /*
                if( tempdpt != row[0] ){
                   str += """
                            name: ${row[0]},
                          """
                   str += """
                            children: [
                                       
                          """
                }else{
                    tempdpt = row[0]
                    if( tempagy != row[1] ){
                           // {
                             //   "name": ${row[0]}, "size": 3938}
                            //}
                    }else{
                        tempagy = row[1]

                    }
                }
                */
            }
            
            /*
            def queryList = query.list()
            queryList.each { row ->
                lst << [
                        row[0] , row[1]
                       ]
            }
            */
           
            str +="""
                                    ]
                     }
                  """
            
        }catch(Exception e){
            println sql
            println e.toString()
        }
        

        //def jsonData = [total: lstNcaList.size(), rows: lstNcaList]

        render str
        
    }
    
    def jsonPopulationTreeMap(){
        def arr             = new ArrayList()
        def parent          = []
        def childrens       = []
        def grandchildrens  = []
        
        def sessionfactory = sessionFactory.currentSession
        def sql1 = ""
        def sql2 = ""
        def sql3 = ""
        
        sql1 = "select a.region,SUM(a.population) from population a group by a.region order by a.region"
        def query1 = sessionfactory.createSQLQuery(sql1)
        query1.list().each { row1 ->
            childrens = []
            def region = row1[0].toString()
            
            sql2 = "select a.city,SUM(a.population) from population a where a.region like '$region' group by a.city order by a.city"
            def query2 = sessionfactory.createSQLQuery(sql2)
            query2.list().each { row2 ->
                grandchildrens = []
                def city = row2[0].toString()
                
                sql3 = "select a.barangay,SUM(a.population) from population a where a.region like '$region' and a.city like '$city' and  a.barangay is not null group by a.barangay order by a.barangay"
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
       
        render """{ "name":"Population", "children":$a}"""
    }
    
    def jsonPopulationByRegion(){
        
        def sessionfactory = sessionFactory.currentSession
        
        def arr = []
        def sql
        
        sql = """
                SELECT z.region,SUM(z.population)
                FROM population z
                WHERE z.region IS NOT NULL
                GROUP BY z.region
                ORDER BY 1
              """
        
              //println sql
        try{
            def query = sessionfactory.createSQLQuery(sql)
            def queryList = query.list()
            queryList.each { row->
                arr << [
                         name  : row[0].toString()
                        ,value : row[1]
                       ]
            }
        }catch(Exception e){
            println "-----------------------------------------"
            println e.toString()
            println "SQL-->"
            //println sql
            println "-----------------------------------------"
        }
        
        render arr as JSON
    } 
    
    def toJson(){
        def arr = []
        arr << [ namex:'A', valuex:10 ]
        arr << [ namex:'A', valuex:3 ]
        arr << [ namex:'A', valuex:4 ]
        arr << [ namex:'A', valuex:5 ]
        arr << [ namex:'B', valuex:7 ]
        arr << [ namex:'C', valuex:4 ]
        arr << [ namex:'C', valuex:9 ]
        
        render arr as JSON
    }
}
