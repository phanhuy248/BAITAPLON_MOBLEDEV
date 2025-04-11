package Models

class User {
    var image: String? = null
    var name: String? = null  // Đổi từ val -> var
    var email: String? = null  // Đổi từ val -> var
    var password: String? = null

    constructor()

    constructor(image: String?, name: String?, email: String?, password: String?) {
        this.image = image
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(name: String?, email: String?, password: String?) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


}
