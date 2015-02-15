#include "ImageLoader.h"

GLuint ImageLoader::loadBMP(char const *name)
{
    BitmapFileHeader bmfh;
    BitmapInfoHeader bmih;

    GLuint texture;
    FILE *in = fopen(name, "rb");
    char *tempData;
    unsigned short bpp;
    int padWidth;
    int byteWidth;
    unsigned int dataSize;

    if (in == NULL) {
        fclose(in);
        return 0;
    }

    fread(&bmfh, sizeof(BitmapFileHeader), 1, in);
    fread(&bmih, sizeof(BitmapInfoHeader), 1, in);
    width = bmih.biWidth;
    height = bmih.biHeight;
    bpp = bmih.biBitCount;
    dataSize = (width * height * (unsigned int) (bmih.biBitCount / 8.0));

    tempData = new char[dataSize];
    fread(tempData, sizeof(char), dataSize, in);
    fclose(in);

    byteWidth = padWidth = (int) ((float) width * (float) bpp / 8.0);

    while (padWidth % 4 != 0) {
        padWidth++;
    }

    char *data = new char[width * height * 3];

    int offset = padWidth - byteWidth;
    for (int i = 0; i < dataSize; i += 3) {
        if ((i + 1) % padWidth == 0) {
            i += offset;
        }
        *(data + i + 2) = *(tempData + i);
        *(data + i + 1) = *(tempData + i + 1);
        *(data + i) = *(tempData + i + 2);
    }

    tempData = data;

    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, tempData);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    free(tempData);
    return texture;
}

GLuint ImageLoader::loadTGA(char const *name)
{
    GLuint texture;
    FILE *in;
    char *tempData;
    in = fopen(name, "rb");

    if (in == NULL) {
        fclose(in);
        return 0;
    }

    TGAHeader header;
    static std::uint8_t DeCompressed[12] = {0x0, 0x0, 0x2, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};
    static std::uint8_t IsCompressed[12] = {0x0, 0x0, 0xA, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};

    fread(&header, sizeof(header), 1, in);

    if (!memcmp(DeCompressed, &header, sizeof(DeCompressed))) {
        width = header.width;
        height = header.height;

        if ((header.bitsperpixel != 24) && (header.bitsperpixel != 32)) {
            fclose(in);
            throw std::invalid_argument("Invalid File Format. Required: 24 or 32 Bit Image.");
        }

        tempData = new char[((width * header.bitsperpixel + 31) / 32) * 4 * height];
        fread(tempData, sizeof(tempData), 1, in);
    }
    else if (!memcmp(IsCompressed, &header, sizeof(IsCompressed))) {
        header.bitsperpixel = header.bitsperpixel;
        width = header.width;
        height = header.height;

        if ((header.bitsperpixel != 24) && (header.bitsperpixel != 32)) {
            fclose(in);
            throw std::invalid_argument("Invalid File Format. Required: 24 or 32 Bit Image.");
        }

        RGBQuad pixel;
        int currentByte = 0;
        int currentPixel = 0;
        std::uint8_t chunkHeader = {0};
        tempData = new char[width * height * sizeof(RGBQuad)];

        while (currentPixel < (width * height)) {
            fread(&chunkHeader, sizeof(chunkHeader), 1, in);

            if (chunkHeader < 128) {
                ++chunkHeader;
                for (int i = 0; i < chunkHeader; ++i, ++currentPixel) {
                    fread(&pixel, header.bitsperpixel / 8, 1, in);

                    tempData[currentByte++] = pixel.red;
                    tempData[currentByte++] = pixel.green;
                    tempData[currentByte++] = pixel.blue;
                    if (header.bitsperpixel > 24) tempData[currentByte++] = pixel.alpha;
                }
            }
            else {
                chunkHeader -= 127;
                fread(&pixel, header.bitsperpixel / 8, 1, in);

                for (int i = 0; i < chunkHeader; ++i, ++currentPixel) {
                    tempData[currentByte++] = pixel.red;
                    tempData[currentByte++] = pixel.green;
                    tempData[currentByte++] = pixel.blue;
                    if (header.bitsperpixel > 24) tempData[currentByte++] = pixel.alpha;
                }
            }
        }
    }
    else {
        fclose(in);
        throw std::invalid_argument("Invalid File Format. Required: 24 or 32 Bit TGA File.");
    }

    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, tempData);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    free(tempData);
    return texture;
}

GLuint ImageLoader::loadPNG(char const *name)
{
    GLuint texture;
    std::vector<unsigned char> tempData;
    unsigned width, height;
    unsigned error = lodepng::decode(tempData, width, height, name);

    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, &tempData[0]);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    return texture;
}