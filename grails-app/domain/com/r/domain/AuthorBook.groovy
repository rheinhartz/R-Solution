package com.r.domain

class AuthorBook {

    static belongsTo = [ author:Author ]

    String title
    boolean isdeleted

    static transients = [ 'isdeleted' ]

    static constraints = {
    }

    static mapping = {
        table 'AUTHOR_BOOK'
        version false
        id column: "author_book_id"
    }

    def String toString() {
        return title
    }
}