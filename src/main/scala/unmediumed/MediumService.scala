package unmediumed

class MediumService {
  def buildPost(html: String): MediumPost = {
    html match {
      case h: String if h != "" => new MediumPost(html)
      case _ => throw new IllegalArgumentException("Creating MediumPost with invalid HTML")
    }
  }
}
