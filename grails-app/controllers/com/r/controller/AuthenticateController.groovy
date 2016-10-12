package com.r.controller

class AuthenticateController {

    def sessionFactory
    
    static allowedMethods = [logIn:'POST']
    
    def index() { 
        redirect(action:"login",params:params)
    }
    
    def login() { 
        
    }
    
    def logIn() {
        
        def sessionfactory = sessionFactory.currentSession
        
        def uname = params.username
        def upass = params.password
        
        def sql = ""
        
        //sql = "select a.* from scott.dept a where 1=1 and a.loc=:cond1"
        sql = """
                SELECT A.username,A.password,A.user_id,A.fname,A.mname,A.lname
                FROM USER A
                WHERE A.username='$uname'
                AND A.password='$upass'
              """
            
        
        try{
            def query = sessionfactory.createSQLQuery(sql.toString())
            //query.setString('cond1', cond1)

            def queryList = query.list()
            
            if( queryList.size() > 0  ){
                
                //Set session for users
                //flash.message = "Accepted"
                queryList.each { row ->
                    session.user_name   = row[0].toString()
                    session.user_pass   = row[1].toString()
                    session.user_id     = row[2].toString()
                    session.user_fullname  = row[3].toString() + " " + row[4].toString() + " " + row[5].toString()
                }
                
                redirect(uri:"/home")
            }else{
                flash.message = "Invalid Username/Password"
                
                redirect(action:"login")
            }
           
        }catch(Exception e){
            println sql
            println e.toString()
        }
        
    }
    
    def logOut() {
        //Destroy session
        session.user_name = null
        redirect(action:"login")
    }

}
