package com.r.domain

import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;

class Author {

    String name
    Integer age
    
    List books = new ArrayList()
    
    static hasMany = [books: AuthorBook]

    static constraints = {
        name(nullable:false,blank: false, unique:true)
    }

    static mapping = {
        table 'AUTHOR'
        version false
        id column: "author_id"
        
        books cascade: "all-delete-orphan"
        //fname column: 'FNAME', sqlType: "varchar", length: 100, defaultValue: 'asd'
        //permissions cascade: "all-delete-orphan"
        //roles cascade: "all-delete-orphan"
    }

    /*
    def getBookList() {
        return LazyList.decorate(
                books,
                FactoryUtils.instantiateFactory(AuthorBook.class))
    }
    */
}
