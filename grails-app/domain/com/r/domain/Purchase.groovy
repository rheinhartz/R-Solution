package com.r.domain

class Purchase {

    Integer purchase_order_id
    String doc_no
    String kind
    String type
    String location_id
    Date date
    Integer isdeleted
    
    List items = new ArrayList()
    
    static hasMany = [items: PurchaseItem]

    static constraints = {
        purchase_order_id(nullable:true, blank: true)
        doc_no(nullable:false, blank: false, unique:true)
        location_id(nullable:true, blank: true)
        date(nullable:false, blank: false)
        kind(nullable:true, blank: true)
        type(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'PURCHASE'
        version false
        id column: "purchase_id"
        
        isdeleted defaultValue: 0
        
        items cascade: "all-delete-orphan"
    }
}
