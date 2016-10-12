package com.r.domain

class PurchaseOrder {

    String doc_no
    String kind
    String type
    String location_id
    String supplier_id
    Date date
    Integer isdeleted
    
    List items = new ArrayList()
    
    static hasMany = [items: PurchaseOrderItem]

    static constraints = {
        doc_no(nullable:false, blank: false, unique:true)
        location_id(nullable:true, blank: true)
        supplier_id(nullable:false, blank: false)
        date(nullable:false, blank: false)
        kind(nullable:true, blank: true)
        type(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'PURCHASE_ORDER'
        version false
        id column: "purchase_order_id"
        
        isdeleted defaultValue: 0
        
        items cascade: "all-delete-orphan"
    }
}
