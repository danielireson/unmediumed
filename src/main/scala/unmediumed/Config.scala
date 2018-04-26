package unmediumed

trait ConfigComponent {
  def config: ConfigLocal

  trait ConfigLocal {
    def get(key: String): Any
  }
}

trait Config extends ConfigComponent {
  def config: Config

  class Config(store: Map[String, Any]) extends ConfigLocal {
    def get(key: String): Any = {
      store.get(key).orElse {
        throw new IllegalArgumentException("Invalid configuration value found")
      }
    }
  }
}
