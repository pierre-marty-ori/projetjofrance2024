package site.JO_France_2024.Utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import site.JO_France_2024.Models.CommandeEntity;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

public class QRCodeGenerator {

    public static String generateQRCode(CommandeEntity commande) throws WriterException, IOException {
        String qrCodePath = "https://projetjofrance2024-production.up.railway.app\\public\\qrcodes\\";
        String qrCodeName = UUID.randomUUID() + "-qrcode.jpeg";
        String qrCodeFilePath = qrCodePath + qrCodeName;
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "Client: " + commande.getUser()+ "\n" +
                        "Clé :" + commande.getKeyCommande()+ "\n" +
                        "Offre :" + commande.getOffre(),
                BarcodeFormat.QR_CODE, 400,400
        );
        Path path = FileSystems.getDefault().getPath(qrCodeFilePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "JPEG", path);
        return qrCodeName;
    }
}
