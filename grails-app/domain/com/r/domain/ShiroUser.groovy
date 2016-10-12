package com.r.domain

class ShiroUser {

    String username
    String password
    String fname
    String mname
    String lname
    Integer isactive
    Integer isdeleted
    
    String cpass
    String npass
    static transients = [ 'cpass', 'npass' ]
    
    List roles = new ArrayList()
    List permissions = new ArrayList()

    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
        password(nullable: false, blank: false)
        fname(nullable: false, blank: false)
        mname(nullable: false, blank: false)
        lname(nullable: false, blank: false)
        isactive(nullable: true)
        isdeleted(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false 
        table 'SECUSER'
        version false
        id column: "user_id"
        
        isactive defaultValue: 1
        isdeleted defaultValue: 0
    }
}
