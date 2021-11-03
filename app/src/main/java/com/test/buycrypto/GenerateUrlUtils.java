package com.test.buycrypto;

import android.util.Base64;

import com.blankj.utilcode.util.LogUtils;

import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created time : 2021/10/29 12:20.
 *
 * @author 10585
 */
public class GenerateUrlUtils {

    private static final String paxfulApiKey = "QbwRgT8a1IpAVQN2e69McuNn22ETQSTk";
    private static final String paxfulApiSecret = "CvMOUYLCZHNxaP0HYnxL2yLbRBQwti7Q";
    private static final String paxfulKioskId = "rDgY4Pb9YJo";

    private static final String MOON_PAY_URL = "https://buy.moonpay.com/?" +
            "currencyCode=%s&" +
            "baseCurrencyAmount=%s&" +
            "baseCurrencyCode=%s&" +
            "walletAddress=%s";
//            +"&apiKey=moonpayApiKey";//

    private static final String MERCURYO_URL = "https://exchange.mercuryo.io/?" +
            "currency=%s&" +
            "fiat_amount=%s&" +
            "fiat_currency=%s&" +
            "address=%s";

    public static String generateUrl(String paymentCode,
                                     String symbol,
                                     String amount,
                                     String currency,
                                     String address) {
        switch (paymentCode) {
            case "Paxful":
                return generateUrl(amount, currency, address);
            case "Moonpay":
                return String.format(MOON_PAY_URL, symbol, amount, currency, address);
            case "Mercuryo":
                return String.format(MERCURYO_URL, symbol, amount, currency, address);
        }
        return "";
    }

    public static String generateUrl(String amount,
                                     String currency,
                                     String address) {

        String data = "apiKey=" + paxfulApiKey + "&ext_crypto_address=" + address
                + "&fiat_amount=" + amount +
                "&fiat_currency=" + currency +
                "&kiosk=" + paxfulKioskId;

        String sha256 = hamcSha256(data, paxfulApiSecret);
        LogUtils.e("sha256====>" + sha256);
        data += "&apiseal=" + sha256;
        return "https://paxful.com/roots/buy-bitcoin?" + data;
    }




    public static String hamcSha256(String data, String key) {

        String algorithm = "HmacSHA256";  // OPTIONS= HmacSHA512, HmacSHA256, HmacSHA1, HmacMD5

        try {

            // 1. Get an algorithm instance.
            Mac sha256_hmac = Mac.getInstance(algorithm);

            // 2. Create secret key.
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), algorithm);

            // 3. Assign secret key algorithm.
            sha256_hmac.init(secret_key);

            // 4. Generate Base64 encoded cipher string.



            return new String(Hex.encode(sha256_hmac.doFinal(data.getBytes("UTF-8"))));

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        } catch (InvalidKeyException e) {

            e.printStackTrace();

        }

        return "";
    }
}