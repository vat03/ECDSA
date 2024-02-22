import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class ECDSA {

    public static void main(String[] args) throws Exception {
        // Generate ECDSA key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1"); // You can choose different curves
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Print public key
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        // Print private key
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        // Signing
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(privateKey, new SecureRandom());
        String message = "Hello, ECDSA!";
        ecdsaSign.update(message.getBytes());
        byte[] signature = ecdsaSign.sign();
        System.out.println("Message: " + message);
        System.out.println("ECDSA Signature: " + Base64.getEncoder().encodeToString(signature));

        // Tamper with the message or signature to simulate incorrect output
        message = "Modified message"; // Tampered message
        // signature[0] = (byte) ~signature[0]; // Uncomment this line to tamper with the signature

        // Verification
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(message.getBytes());
        boolean verified = ecdsaVerify.verify(signature);
        System.out.println("Tampered Message: " + message);
        System.out.println("Signature verified: " + verified);
    }
}
