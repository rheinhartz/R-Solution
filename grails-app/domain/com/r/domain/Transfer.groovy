package com.r.domain

class Transfer {

    String doc_no
    Integer location_id_fr
    Integer location_id_to
    String kind
    String type
    Date date
    Integer isdeleted
    
    List items = new ArrayList()
    
    static hasMany = [items: TransferItem]

    static constraints = {
        doc_no(nullable:false, blank: false, unique:true)
        location_id_fr(nullable:false, blank: false)
        location_id_to(nullable:false, blank: false)
        date(nullable:false, blank: false)
        kind(nullable:true, blank: true)
        type(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'TRANSFER'
        version false
        id column: "transfer_id"
        
        isdeleted defaultValue: 0
        
        items cascade: "all-delete-orphan"
    }
}
