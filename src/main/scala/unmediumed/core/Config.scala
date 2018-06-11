package unmediumed.core

class Config(params: String = "") {
  val store: Map[String, String] = loadStore(params)

  def get(key: String): String = {
    store.getOrElse(key, throw new IllegalArgumentException("Invalid configuration key"))
  }

  private def loadStore(params: String): Map[String, String] = {
    params.split(",").foldLeft(Map.empty[String, String]) {(params, param) =>
      val components = param.split("=")

      if (components.length == 2) {
        Map(components(0) -> components(1))
      } else {
        params
      }
    }
  }
}
