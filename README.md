# EyojInteractiveWordGames

EyojInteractiveWordGames, kullanıcıların farklı dillerdeki kelimeleri öğrenmelerine yardımcı olan interaktif bir kelime oyunu uygulamasıdır. Uygulama, kullanıcıların kelimeleri "öğrenildi" olarak işaretleyerek ilerlemelerini takip etmelerine ve kelimeleri "Öğrenilen Kelimeler" ve "Öğrenilecek Kelimeler" listelerine ayırmalarına olanak tanır.

## Özellikler

- **Kelime Kartları Yönetimi**: Kelime kartları, `wordName`, `isEnglishLearned`, ve `isGermanLearned` gibi özelliklerle Room veritabanında saklanır.
- **Öğrenilen / Öğrenilecek Kelimeler**: Kullanıcılar bir kelimeyi öğrenildi ya da öğrenilecek olarak işaretleyebilir. Bu işlem, kelimenin "Öğrenilecek Kelimeler" listesinden "Öğrenilen Kelimeler" listesine otomatik olarak taşınmasını sağlar.
- **Çok Dilli Destek**: Uygulama, İngilizce ve Almanca gibi diller için kelime öğrenme imkanı sunar.
- **Room Veritabanı Kullanımı**: Kelime kartları yalnızca bir kez eklenir ve kartların öğrenme durumu (ör. İngilizce öğrenildi mi?) güncellenebilir.
- **Eşzamanlı Liste Güncelleme**: "Öğrenilecek Kelimeler" listesinden bir kelime öğrenildi olarak işaretlendiğinde, kelime "Öğrenilen Kelimeler" listesine taşınır. Tersi durumda da kelimeler listeler arasında dinamik olarak taşınır.

## Kurulum

Projeyi yerel ortamınızda çalıştırmak için şu adımları izleyin:

### Gereksinimler

- Android Studio Arctic Fox veya daha yeni bir sürümü
- Java 11 veya daha yeni bir sürümü
- Kotlin 1.5 veya daha yeni bir sürümü

### Adımlar

1. **Projenin klonlanması:**

   ```bash
   git clone https://github.com/EyoJneSinE/EyojInteractiveWordGames.git
