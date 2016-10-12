package com.r.domain

class Physical {

    String doc_no
    String kind
    String type
    Integer location_id
    Date date
    Integer isdeleted
    
    List items = new ArrayList()
    
    static hasMany = [items: PhysicalItem]

    static constraints = {
        doc_no(nullable:false, blank: false, unique:true)
        location_id(nullable:false, blank: false)
        date(nullable:false, blank: false)
        kind(nullable:true, blank: true)
        type(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'PHYSICAL'
        version false
        id column: "physical_id"
        
        isdeleted defaultValue: 0
        
        items cascade: "all-delete-orphan"
    }
}
