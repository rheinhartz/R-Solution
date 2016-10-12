package com.r.domain

class Withdrawal {

    String doc_no
    String kind
    String type
    Integer location_id
    Integer contact_id
    Date date
    Integer isdeleted
    
    List items = new ArrayList()
    
    static hasMany = [items: WithdrawalItem]

    static constraints = {
        doc_no(nullable:false, blank: false, unique:true)
        location_id(nullable:false, blank: false)
        contact_id(nullable:false, blank: false)
        date(nullable:false, blank: false)
        kind(nullable:true, blank: true)
        type(nullable:true, blank: true)
        isdeleted(nullable:true, blank: false)
    }

    static mapping = {
        table 'WITHDRAWAL'
        version false
        id column: "withdrawal_id"
        
        isdeleted defaultValue: 0
        
        items cascade: "all-delete-orphan"
    }
}
