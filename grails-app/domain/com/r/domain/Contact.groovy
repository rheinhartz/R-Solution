package com.r.domain

class Contact {

    String name
    String type
    String kind
    Integer iscustomer
    Integer issupplier
    Integer isemployee
    Integer isdeleted
    
    static constraints = {
        name(nullable: false, blank: false, unique:true)
        type(nullable: true, blank: true)
        kind(nullable: true, blank: true)
        iscustomer(nullable: true, blank: true)
        issupplier(nullable: true, blank: true)
        isemployee(nullable: true, blank: true)
        isdeleted(nullable: true, blank: true)        
    }

    static mapping = {
        table 'CONTACT'
        version false
        id column: "contact_id"
        
        isdeleted defaultValue: 0
    }

}
