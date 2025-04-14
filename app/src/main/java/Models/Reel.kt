package Models

class Reel {
    var reelUrl: String = ""
    var caption: String = ""
    var profileLink: String? = null
    var name: String = ""
    var time: String = ""

    constructor()

    constructor(reelUrl: String, caption: String) {
        this.reelUrl = reelUrl
        this.caption = caption
    }

    constructor(reelUrl: String, caption: String, profileLink: String?) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profileLink = profileLink
    }

    constructor(
        reelUrl: String,
        caption: String,
        profileLink: String?,
        name: String,
        time: String
    ) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profileLink = profileLink
        this.name = name
        this.time = time
    }
}
