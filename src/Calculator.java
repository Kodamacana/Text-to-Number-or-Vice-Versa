import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    //amaç string ile çağrışım yaparak int dönüş alınabilecek bir sözlük elde etmek
    private static final Map<String, Integer> NumberDictionary = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Yazdırmak İstediğiniz Sayının Okunuşunu Girin: ");

        try (Scanner scanner = new Scanner(System.in)) {
            String NumberText = scanner.nextLine();
            scanner.close();

            // Sözlük oluşturmak için elle doldurulan static değerler NumberDictionary Listesine aktarılmak üzere bu fonksiyon çağrılır.
            CreateDictionary();

            int Number = TextToNumber(NumberText.toLowerCase());

            if (Number != Integer.MIN_VALUE) {
                System.out.println("İstenen Sayının Yazılışı: " + NumberText.toLowerCase());
                System.out.println("Sayısal Karşılık: " +"\u001B[33m"+ Number + "\u001B[0m");
            }
            else {
                System.out.println("Geçersiz Okunuş Girdiniz!");
            }
        }
        catch (Exception e){
            System.out.println("Bir Hata Oluştu, Hata Mesajı: " +"\u001B[33m"+ e + "\u001B[0m");
        }
    }

    //Sayılar olarak kullanılan değerler static olarak buradan listeye ekleniyor.
    static void CreateDictionary() {

        NumberDictionary.put("eksi", -0);
        NumberDictionary.put("sıfır", 0);
        NumberDictionary.put("bir", 1);
        NumberDictionary.put("iki", 2);
        NumberDictionary.put("üç", 3);
        NumberDictionary.put("dört", 4);
        NumberDictionary.put("beş", 5);
        NumberDictionary.put("altı", 6);
        NumberDictionary.put("yedi", 7);
        NumberDictionary.put("sekiz", 8);
        NumberDictionary.put("dokuz", 9);
        NumberDictionary.put("on", 10);
        NumberDictionary.put("yirmi", 20);
        NumberDictionary.put("otuz", 30);
        NumberDictionary.put("kırk", 40);
        NumberDictionary.put("elli", 50);
        NumberDictionary.put("altmış", 60);
        NumberDictionary.put("yetmiş", 70);
        NumberDictionary.put("seksen", 80);
        NumberDictionary.put("doksan", 90);
        NumberDictionary.put("yüz", 100);
        NumberDictionary.put("bin", 1000);
        NumberDictionary.put("milyon", 1000000);
    }

    //listeden verilere erişebilmek adına eklenen Get Fonksiyonu
    static int GetDictionaryValue(String word) {
        return NumberDictionary.get(word);
    }

    //Girilen yazıyı sözlükten tarama yaparak sayıya çeviren fonksiyon
    static int TextToNumber(String NumberText) {

        String[] SortingNumberWords = {"milyon", "bin", "yüz"}; //özel sıralama değerleri burada tutuluyor.
        String[] SplitWords = NumberText.split(" "); //ekranda girilen yazılar burada kesilerek sözlükte aratılmak üzere tutuluyor.
        String[] TemporaryWords = new String[SplitWords.length]; //Sıralama sırasında hesaplama yapılabilme ihtimaline karşın gecici kelimeler burada tutuluyor.

        int Total = 0,provisionalTotal = 0,provisionalNumberTotal = 0,temporaryWordOrder = 0,selectedSplitWord = 0,DictionaryNumber = 0;
        String SortingWord;


        //Buradaki algoritma mantığı:
        //Özel kelimelerden sırasıyla kontrolü yapılan yazı girdisi gecici sayıların tutulduğu TemporaryWords de tutuluyor
        //tutulan sayılar aranan özel kelime bulunduğu anda hesaplamaya başlanıyor örneğin:

        //milyon kelimesi aranıyor olsun ve kullanıcının girdiği değer: sekiz milyon yedi yüz yetmiş yedi bin altmış altı olsun.
        //Bu durumda öncelikle aranması gereken değer milyon değeri. Yani milyon yazısı bulunana kadar başına gelen tüm değerler TemporaryWords listesinde tutuluyor.
        //Bunun nedeni milyon değeri bulunduğunda elde edilen değerler ile çarpılması.

        //milyondan sonra aranması gereken özel kelime bin olduğu için milyona kadar olan değer Total değişkeninde tutulmuştur ve TemporaryWords boşaltılmıştır.

        //yedi, yüz, yetmiş, yedi kelimeleri TemporaryWords listesine atanması ile "bin" kelimesi bulunmuştur.
        //Sırasıyla kelimeler alınarak özel olup olmaması durumları kontrol ediliyor. Örneğin yedi kelimesi özel olmadığı için provisionalNumberTotal değişkenine
        //ekleniyor. "yüz" kelimesi özel olduğu için 100 ile provisionalNumberTotal değişkeni çarpılarak provisionalNumberTotal değişkenine atanıyor.


        for (int k = 0; k < SortingNumberWords.length; k++) {

            temporaryWordOrder = 0;
            TemporaryWords = new String[SplitWords.length];
            SortingWord = SortingNumberWords[k];

            //girdisi alınan kelimeler atanıyor
            for (int i = selectedSplitWord; i < SplitWords.length; i++) {
                String Sword = SplitWords[i]; //girdi yapılan cümlenin belirlenen kelimesi özel mi diye kontrol edilmek üzere değişkene aktarılıyor

                //kelime özel kelime ise hesaplama işlemi başlatılıyor.
                if (SortingWord.equals(Sword)) {

                    //Geçici kelimeler for ile döndürülerek sözlükteki karşılığı bulunuyor ardından değerleri geçeici toplam değer değişkenine atanıyor.
                    for (String word : TemporaryWords) {
                        if (word != null) {
                            DictionaryNumber = GetDictionaryValue(word);

                            if (DictionaryNumber == 100 ||DictionaryNumber == 1000 ||DictionaryNumber == 1000000 ) {
                                provisionalNumberTotal *= DictionaryNumber;
                            } else {
                                provisionalNumberTotal += DictionaryNumber;
                            }
                        }
                    }

                    if (provisionalNumberTotal == 0) {
                        provisionalTotal = GetDictionaryValue(SortingWord);
                    } else {
                        provisionalTotal = provisionalNumberTotal * GetDictionaryValue(SortingWord);
                    }

                    //değişkenler sıfırlanarak tekrar hesap yapılabilir hale getiriliyor.

                    Total += provisionalTotal;
                    provisionalNumberTotal = 0;
                    provisionalTotal = 0;
                    temporaryWordOrder = 0;
                    selectedSplitWord = i + 1;
                    TemporaryWords = new String[SplitWords.length];

                }
                // Kelime özel değil ise TemporaryWords listesine ekleniyor.
                else {
                    TemporaryWords[temporaryWordOrder] = Sword;
                    temporaryWordOrder++;
                }
            }
        }


        //özel kelime araması tamamlanamamış olan diğer kelimelerin karşılıkları burada bulunarak Total değişkenine ekleniyor.
        if (TemporaryWords[0] != null) {
            for (String word : TemporaryWords) {
                if (word != null) {
                    DictionaryNumber = GetDictionaryValue(word);
                    provisionalNumberTotal += DictionaryNumber;
                }
            }
            Total += provisionalNumberTotal;
        }


        //kelime negatif mi pozitif mi olduğu burada belirleniyor. Buna göre Total değişkeninin değeri değişiyor.
        if (SplitWords[0].equals("eksi")) Total = -Total;

        return Total;
    }
}
