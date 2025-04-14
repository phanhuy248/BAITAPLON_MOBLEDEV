package Models

class Post {
    var postId: String = ""
    var postUrl:String=""
    var caption:String=""
    var uid:String=""
    var time :String=""
    constructor()
    constructor(postUrl: String, caption: String) {
        this.postUrl = postUrl
        this.caption = caption
    }

    constructor(caption: String, postUrl: String, time: String, uid: String) {
        this.caption = caption
        this.postUrl = postUrl
        this.time = time
        this.uid = uid
    }

    constructor(postId: String) {
        this.postId = postId
    }


}