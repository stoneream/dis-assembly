package app

import java.awt.Color
import java.nio.file.Paths
import javax.imageio.ImageIO

object Main extends App {

  val filePath = Paths.get("./image.jpg")
  val file = filePath.toFile
  val image = ImageIO.read(file)

  val positions = (image.getMinY until image.getHeight).map { y =>
    (image.getMinX until image.getWidth).map { x =>
      (x, y)
    }
  }.flatten

  val colorCodes = positions.map { case (x, y) => image.getRGB(x, y) }.groupBy(c => c).toList.sortBy { case (_, n) => n.size }

  colorCodes.foreach { case (rgb, n) =>
    val color = new Color(rgb)
    println(s"#${color.getRed.toHexString}${color.getGreen.toHexString}${color.getBlue.toHexString}, ${n.size}")
  }
}
