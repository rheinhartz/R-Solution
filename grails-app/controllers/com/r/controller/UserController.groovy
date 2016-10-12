package com.r.controller

import com.r.domain.ShiroUser
import com.r.domain.ShiroRole

import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

class UserController {

    def MainService
    
    def index() {
        redirect(action: "show", params: params)
    }

    def show(Integer max) {

        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }
    
    def list() {
        
        def listField = new ArrayList()
        listField.add "user_id"
        listField.add "username"
        listField.add "fname"
        listField.add "mname"
        listField.add "lname"
        
        def list = mainService.listTable("secuser",listField," AND a.isdeleted != 1")

        [list: list]
        //params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def add() {
        def userInstance = new ShiroUser()
        userInstance.properties = params
        
        def controllerList = mainService.getControllerNames()
        def actionList = mainService.getControllerActions()
        
        return [ 
                    userInstance : userInstance
                   ,controllerList : controllerList
                   ,actionList : actionList
               ]
    }
    
    def edit() {
        
        def userInstance = ShiroUser.get( params.id )
        
        def controllerList = mainService.getControllerNames()
        def actionList = mainService.getControllerActions()

        if(!userInstance) {
            flash.message = "User not found with id ${params.id}"
            redirect(action:"view")
        }
        else {
            return [ 
                         userInstance : userInstance
                        ,controllerList : controllerList
                        ,actionList : actionList
                   ]
        }
    }
    
    def delete() {
        
    }
    
    def view() {
        def listField = new ArrayList()
        listField.add "user_id"
        listField.add "username"
        listField.add "fname"
        listField.add "mname"
        listField.add "lname"
        
        def list = mainService.listTable("secuser",listField," AND a.isdeleted != 1")

        [list: list]
    }
    
    def save() {
        
        def controllerList  = mainService.getControllerNames()
        def actionList      = mainService.getControllerActions()
        
        def act = params.act
        def userInstance
        
        params.isactive     = (params.isactive == null) || (params.isactive == "") ? 0 : 1
        params.isdeleted    = (params.isdeleted == null) || (params.isdeleted == "") ? 0 : 1
        
        if( act == "add" ){
            params.password     = new Sha256Hash(params.password).toHex()
            
            userInstance = new ShiroUser( params )
       
            if(!userInstance.hasErrors() && userInstance.save()) {
                flash.message = "User added"
                redirect(action: "view", params: params)
            }
            else {
                //flash.message = "User Error"   
                render( view:'add'
                       ,model:[
                                 userInstance   : userInstance
                                ,controllerList : controllerList
                                ,actionList     : actionList
                              ]
                      )
            }
        }
           
        if( act == "edit" ){
            
            def user = ShiroUser.findById(params.id)
            params.password     = (params.password == "") ? user.password : new Sha256Hash(params.password).toHex()
            
            userInstance = ShiroUser.get( params.id )
            
            if(userInstance) {
                userInstance.properties = params

                //def _toBeDeleted = userInstance.permissions.findAll { (it || (it == null)) }
                //println _toBeDeleted.toString()
                /*
                def _toBeDeleted = userInstance.permissions.findAll { (it?.isdeleted || (it == null)) }
                println _toBeDeleted.toString()
                if (_toBeDeleted) {
                    authorInstance.permissions.removeAll(_toBeDeleted)
                }
                */
                //println it?.name
                //println "This to be deleted"
                //print "id:"
                /*
                def _toBeDeleted = authorInstance.books.findAll {(it?.isdeleted || (it == null))}
                if (_toBeDeleted) {
                    print " - true"
                    authorInstance.books.removeAll(_toBeDeleted)
                }else{
                    print " - false"
                }
                */
                
                if(!userInstance.hasErrors() && userInstance.save()) {
                    flash.message = "User updated"
                    redirect(action: "view", params: params)
                }
                else {
                    //flash.message = "User Error"   
                    render(view:'edit',model:[userInstance:userInstance])
                }

            }
            else {
                flash.message = "User not found with id ${params.id}"
                redirect(action:"view")
            }
        }
        
        if( act == "delete" ){
            
        }
       
    }
}
