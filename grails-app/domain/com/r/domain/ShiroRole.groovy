package com.r.domain

class ShiroRole {
    
    String name

    List permissions = new ArrayList()
    
    static hasMany = [ users: ShiroUser, permissions: String ]
    static belongsTo = ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }

    static mapping = {
        table 'SECUSER_ROLE'
        version false
        id column: "user_role_id"
    }
}
