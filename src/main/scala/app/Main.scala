package app

import java.awt.image.BufferedImage
import java.awt.Color
import java.nio.file.Paths
import javax.imageio.ImageIO
import scala.swing.{Dimension, Frame, Graphics2D, GridPanel, MainFrame, Panel, SimpleSwingApplication}

//object Main extends App {
//
//  val filePath = Paths.get("./image.jpg")
//  val file = filePath.toFile
//  val image = ImageIO.read(file)
//
//  val positions = (image.getMinY until image.getHeight).map { y =>
//    (image.getMinX until image.getWidth).map { x =>
//      (x, y)
//    }
//  }.flatten
//
//  val colorCodes = positions.map { case (x, y) => image.getRGB(x, y) }.groupBy(c => c).toList.sortBy { case (_, n) => n.size }
//
//  colorCodes.foreach { case (rgb, n) =>
//    val color = new Color(rgb)
//    println(s"#${color.getRed.toHexString}${color.getGreen.toHexString}${color.getBlue.toHexString}, ${n.size}")
//  }
//}

object Main extends SimpleSwingApplication {
  val filePath = Paths.get("./image.jpg")
  val file = filePath.toFile
  val original = ImageIO.read(file)

  val windowWidth = original.getWidth * 2
  val windowHeight = original.getHeight + 100

  override def top: Frame = new MainFrame {
    title = "dis-assembly"
    contents = new GridPanel(1, 2) {
      contents += new OriginalImage(original)
      contents += new Disassembly(original)
    }
    size = new Dimension(windowWidth, windowHeight)
  }
}

class OriginalImage(image: BufferedImage) extends Panel {
  override def paint(g: Graphics2D): Unit = {
    g.drawImage(image, null, 0, 0)
  }
}

class Disassembly(originalImage: BufferedImage) extends Panel {
  val positions = (originalImage.getMinY until originalImage.getHeight).map { y =>
    (originalImage.getMinX until originalImage.getWidth).map { x =>
      (x, y)
    }
  }.flatten

  override def paint(g: Graphics2D): Unit = {
    val colors = positions
      .map { case (x, y) =>
        originalImage.getRGB(x, y)
      }
      .groupBy(argb => argb)
      .toList
      .sortBy { case (argb, n) => n.size }
      .map { case (argb, n) => List.fill(n.size)(new Color(argb, true)) }
      .flatten

    colors.zipWithIndex.foreach { case (color, index) =>
      g.setColor(color)
      val x = index % originalImage.getWidth
      val y = index / originalImage.getWidth

      g.fillRect(x, y, 1, 1)
    }
  }
}
