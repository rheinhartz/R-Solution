package com.r.controller

import com.r.domain.Author
import groovy.sql.Sql
import groovy.xml.MarkupBuilder
import org.springframework.dao.DataIntegrityViolationException
import javax.swing.JOptionPane;

import com.r.domain.Population

class AuthorController {

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']
    
    def index = {
        //def datasource = grailsApplication.mainContext.getBean('dataSource')
        //def conn = Sql.newInstance(datasource)
        //conn.execute("call procedure2(asd)")
        
        def regions = [
                        "REGIONI"
                       ,"REGIONII"
                       ,"REGIONIII"
                       ,"REGIONIVA"
                       ,"REGIONIVB"
                       ,"REGIONV"
                       ,"REGIONVI"
                       ,"REGIONVII"
                       ,"REGIONVIII"
                       ,"REGIONIX"
                       ,"REGIONX"
                       ,"REGIONXI"
                       ,"REGIONXII"
                       ,"REGIONXIII"
                       ,"NCR"
                       ,"CAR"
                       ,"ARMM"
                      ]
                     
        for(i in 1..200){
            def min = 0
            def max = 16
            def region = ( Math.random() * (max - min + 1) + min ).toInteger()

            def r = regions[region]
            def c = "CITY" + ( Math.random() * (1 - 100 + 1) + 1 ).toInteger()
            def b = "BARANGAY" + ( Math.random() * (1 - 100 + 1) + 1 ).toInteger()
            def p = ( Math.random() * (1000 - 1 + 1) + 1 ).toInteger()
            
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
        
            def barangay_chance = ( Math.random() * (100 - 1 + 1) + 1 ).toInteger()
            
            if( barangay_chance <= 30 ){
                //new Population(region: r, city: c, population: p).save()
                conn.execute("INSERT INTO population(region,city,population) VALUES('$r','$c',$p)")
            }else{
                //new Population(region: r, city: c, barangay: b, population: p).save()
                conn.execute("INSERT INTO population(region,city,barangay,population) VALUES('$r','$c','$b',$p)")
            }
            
            
        }
        
        redirect(action:list,params:params)
    }

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ authorInstanceList: Author.list( params ), authorInstanceTotal: Author.count() ]
    }

    def show = {
        def authorInstance = Author.get( params.id )

        if(!authorInstance) {
            flash.message = "Author not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ authorInstance : authorInstance ] }
    }

    def delete = {
        def authorInstance = Author.get( params.id )
        if(authorInstance) {
            try {
                authorInstance.delete(flush:true)
                flash.message = "Author ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Author ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Author not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def authorInstance = Author.get( params.id )

        if(!authorInstance) {
            flash.message = "Author not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ authorInstance : authorInstance ]
        }
    }

    def update = {
        def authorInstance = Author.get( params.id )
        if(authorInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(authorInstance.version > version) {

                    authorInstance.errors.rejectValue("version", "author.optimistic.locking.failure", "Another user has updated this Author while you were editing.")
                    render(view:'edit',model:[authorInstance:authorInstance])
                    return
                }
            }
            authorInstance.properties = params

            //println authorInstance.id
            //println authorInstance?.bookList

            try{
                println authorInstance?.books?.title
                println authorInstance?.books?.kind
                println authorInstance?.books?.isdeleted
            }catch (Exception e){

            }

            //println it?.name
            //println "This to be deleted"
            //print "id:"
            def _toBeDeleted = authorInstance.books.findAll {(it?.isdeleted || (it == null))}
            if (_toBeDeleted) {
                print " - true"
                authorInstance.books.removeAll(_toBeDeleted)
            }else{
                print " - false"
            }

            if(!authorInstance.hasErrors() && authorInstance.save()) {
                flash.message = "Author ${params.id} updated"
                redirect(action:show,id:authorInstance.id)
            }
            else {
                render(view:'edit',model:[authorInstance:authorInstance])
            }
        }
        else {
            flash.message = "Author not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def authorInstance = new Author()
        authorInstance.properties = params
        return ['authorInstance':authorInstance]
    }

    def save = {
        def authorInstance = new Author(params)

        //JOptionPane.showMessageDialog(null, authorInstance.books );

        if(!authorInstance.hasErrors() && authorInstance.save()) {
            flash.message = "Author ${authorInstance.id} created"
            redirect(action:show,id:authorInstance.id)
        }
        else {
            render(view:'create',model:[authorInstance:authorInstance])
        }

    }
}
