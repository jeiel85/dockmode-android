from __future__ import annotations

from math import cos, sin, tau
from pathlib import Path

from PIL import Image, ImageDraw, ImageFilter, ImageFont


ROOT = Path(__file__).resolve().parents[1]
OUT_DIR = ROOT / "play_store" / "graphics"
SCREENSHOT_DIR = ROOT / "play_store" / "screenshots"

BLACK = (0, 0, 0)
OBSIDIAN = (12, 12, 18)
CARD = (22, 22, 34)
BLUE = (91, 156, 255)
INDIGO = (129, 140, 248)
TEAL = (14, 165, 233)
IVORY = (237, 237, 242)
GREY = (138, 138, 154)
AMBER = (245, 158, 11)


def font(size: int, bold: bool = False) -> ImageFont.FreeTypeFont:
    candidates = [
        Path("C:/Windows/Fonts/segoeuib.ttf" if bold else "C:/Windows/Fonts/segoeui.ttf"),
        Path("C:/Windows/Fonts/arialbd.ttf" if bold else "C:/Windows/Fonts/arial.ttf"),
    ]
    for candidate in candidates:
        if candidate.exists():
            return ImageFont.truetype(str(candidate), size=size)
    return ImageFont.load_default(size=size)


def gradient(size: tuple[int, int], top: tuple[int, int, int], bottom: tuple[int, int, int]) -> Image.Image:
    width, height = size
    image = Image.new("RGB", size, top)
    pixels = image.load()
    for y in range(height):
        t = y / max(1, height - 1)
        row = tuple(int(top[i] * (1 - t) + bottom[i] * t) for i in range(3))
        for x in range(width):
            pixels[x, y] = row
    return image.convert("RGBA")


def glow_layer(size: tuple[int, int], circles: list[tuple[int, int, int, tuple[int, int, int], int]]) -> Image.Image:
    layer = Image.new("RGBA", size, (0, 0, 0, 0))
    draw = ImageDraw.Draw(layer)
    for x, y, radius, color, alpha in circles:
        draw.ellipse((x - radius, y - radius, x + radius, y + radius), fill=(*color, alpha))
    return layer.filter(ImageFilter.GaussianBlur(42))


def draw_clock_mark(draw: ImageDraw.ImageDraw, center: tuple[int, int], radius: int, scale: float = 1.0) -> None:
    cx, cy = center
    ring_width = max(4, int(14 * scale))
    draw.ellipse(
        (cx - radius, cy - radius, cx + radius, cy + radius),
        outline=(*BLUE, 255),
        width=ring_width,
    )
    inner_radius = int(radius * 0.73)
    for angle in range(0, 360, 30):
        length = int(10 * scale) if angle % 90 else int(17 * scale)
        a = (angle - 90) / 360 * tau
        x1 = cx + int(cos(a) * (inner_radius - length))
        y1 = cy + int(sin(a) * (inner_radius - length))
        x2 = cx + int(cos(a) * inner_radius)
        y2 = cy + int(sin(a) * inner_radius)
        draw.line((x1, y1, x2, y2), fill=(*IVORY, 210), width=max(2, int(4 * scale)))
    draw.line(
        (cx, cy, cx, cy - int(radius * 0.55)),
        fill=(*IVORY, 255),
        width=max(5, int(13 * scale)),
        joint="curve",
    )
    draw.line(
        (cx, cy, cx + int(radius * 0.46), cy + int(radius * 0.23)),
        fill=(*IVORY, 255),
        width=max(5, int(13 * scale)),
        joint="curve",
    )
    draw.ellipse((cx - int(8 * scale), cy - int(8 * scale), cx + int(8 * scale), cy + int(8 * scale)), fill=(*IVORY, 255))


def make_icon() -> None:
    size = 512
    image = gradient((size, size), OBSIDIAN, BLACK)
    image.alpha_composite(glow_layer((size, size), [(110, 110, 120, BLUE, 90), (420, 118, 140, INDIGO, 70), (320, 420, 180, TEAL, 52)]))
    draw = ImageDraw.Draw(image)

    draw.rounded_rectangle((34, 34, 478, 478), radius=108, outline=(*IVORY, 26), width=2)
    draw.rounded_rectangle((76, 92, 436, 382), radius=54, fill=(*CARD, 214), outline=(*IVORY, 34), width=2)
    draw.rounded_rectangle((156, 389, 356, 424), radius=18, fill=(*BLUE, 255))
    draw.rounded_rectangle((106, 431, 406, 456), radius=13, fill=(*IVORY, 238))
    draw.rectangle((190, 382, 322, 406), fill=(*BLUE, 255))

    draw_clock_mark(draw, (256, 236), 118, 1.0)
    draw.rounded_rectangle((177, 330, 335, 351), radius=10, fill=(*AMBER, 235))
    draw.rounded_rectangle((207, 358, 305, 369), radius=5, fill=(*GREY, 160))

    image.save(OUT_DIR / "app-icon-512.png")


def screenshot_card(path: Path, target_size: tuple[int, int], radius: int) -> Image.Image:
    shot = Image.open(path).convert("RGBA")
    shot.thumbnail(target_size, Image.Resampling.LANCZOS)
    card = Image.new("RGBA", target_size, (0, 0, 0, 0))
    x = (target_size[0] - shot.width) // 2
    y = (target_size[1] - shot.height) // 2
    bg = Image.new("RGBA", target_size, (*CARD, 235))
    mask = Image.new("L", target_size, 0)
    ImageDraw.Draw(mask).rounded_rectangle((0, 0, target_size[0] - 1, target_size[1] - 1), radius=radius, fill=255)
    card.alpha_composite(bg)
    card.alpha_composite(shot, (x, y))
    card.putalpha(mask)
    return card


def make_feature_graphic() -> None:
    width, height = 1024, 500
    image = gradient((width, height), (8, 8, 13), BLACK)
    image.alpha_composite(glow_layer((width, height), [(158, 70, 180, BLUE, 70), (900, 90, 220, INDIGO, 58), (710, 440, 190, TEAL, 44)]))
    draw = ImageDraw.Draw(image)

    for offset, alpha in [(0, 34), (40, 22), (80, 16)]:
        draw.rounded_rectangle((500 + offset, 70 + offset // 3, 994 + offset, 430 + offset // 4), radius=38, outline=(*IVORY, alpha), width=2)

    title_font = font(74, bold=True)
    sub_font = font(28)
    small_font = font(22)
    draw.text((70, 132), "DockMode", fill=(*IVORY, 255), font=title_font)
    draw.text((75, 226), "Dock clock and calendar", fill=(*GREY, 255), font=sub_font)
    draw.rounded_rectangle((76, 288, 304, 336), radius=24, fill=(*BLUE, 238))
    draw.text((105, 299), "Android native", fill=(5, 9, 20, 255), font=small_font)

    icon = Image.open(OUT_DIR / "app-icon-512.png").convert("RGBA").resize((92, 92), Image.Resampling.LANCZOS)
    image.alpha_composite(icon, (76, 30))

    minimal = screenshot_card(SCREENSHOT_DIR / "03-standby-minimal.png", (454, 210), 30)
    digital = screenshot_card(SCREENSHOT_DIR / "04-standby-digital.png", (454, 210), 30)
    shadow = Image.new("RGBA", (500, 250), (0, 0, 0, 0))
    sd = ImageDraw.Draw(shadow)
    sd.rounded_rectangle((20, 20, 480, 230), radius=34, fill=(0, 0, 0, 120))
    shadow = shadow.filter(ImageFilter.GaussianBlur(16))

    image.alpha_composite(shadow, (526, 72))
    image.alpha_composite(minimal, (544, 82))
    image.alpha_composite(shadow, (466, 224))
    image.alpha_composite(digital, (484, 234))

    draw.rounded_rectangle((544, 82, 997, 291), radius=30, outline=(*BLUE, 140), width=2)
    draw.rounded_rectangle((484, 234, 937, 443), radius=30, outline=(*INDIGO, 130), width=2)
    image.save(OUT_DIR / "feature-graphic-1024x500.png")


def main() -> None:
    OUT_DIR.mkdir(parents=True, exist_ok=True)
    make_icon()
    make_feature_graphic()


if __name__ == "__main__":
    main()
