package unmediumed.core

trait ConfigComponent {
  val config: Config

  class Config(store: Map[String, Any]) {
    def get(key: String): Any = {
      store.get(key).orElse {
        throw new IllegalArgumentException("Invalid configuration key")
      }
    }
  }
}
