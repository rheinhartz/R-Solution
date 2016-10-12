package com.r.domain

class Adjust {

    String doc_no
    String kind
    String type
    String remark
    Integer location_id
    Date date
    Integer isdeleted
    
    List items = new ArrayList()
    
    static hasMany = [items: AdjustItem]

    static constraints = {
        doc_no(nullable:false, blank: false, unique:true)
        remark(nullable:true, blank: true)
        location_id(nullable:false, blank: false)
        date(nullable:false, blank: false)
        kind(nullable:true, blank: true)
        type(nullable:false, blank: false)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'ADJUST'
        version false
        id column: "adjust_id"
        
        isdeleted defaultValue: 0
        
        items cascade: "all-delete-orphan"
    }
}
