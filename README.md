Asisten Keuangan ✨ (Financial Assistant - Anime Edition)
Aplikasi pencatat keuangan harian Android yang modern, profesional, dan estetik dengan tema Anime Red. Membantu Anda mengelola pemasukan dan pengeluaran dengan antarmuka yang bersih serta visualisasi data yang interaktif.
📸 Tampilan Utama
•
Tema: Anime Kawaii (Glassmorphism Style)
•
Warna Dominan: Merah (#FF4D4D)
•
Background: Custom Anime Wallpaper (background.jpg)
🚀 Fitur Unggulan
•
Dashboard Saldo Real-time: Pantau total saldo (Pemasukan - Pengeluaran) secara otomatis.
•
Pemisahan Tab Transaksi: Input Pemasukan 💰 dan Pengeluaran 💸 dipisahkan dengan navigasi TabLayout yang mulus.
•
Manajemen Data Lengkap: Fitur Tambah, Edit, dan Hapus transaksi dengan mudah.
•
Kategori Berbasis Emoji: Mempermudah identifikasi transaksi (Makan 🍱, Belanja 🛍️, Gaji 💰, dll).
•
Ringkasan Bulanan & Grafik: Visualisasi proporsi pengeluaran menggunakan Pie Chart 📊 interaktif (MPAndroidChart).
•
Penyimpanan Lokal: Menggunakan Room Database, data tersimpan aman di perangkat meskipun tanpa internet.
•
Swipe to Action: Dukungan fitur geser untuk navigasi lebih cepat.
🛠️ Tech Stack
Aplikasi ini dibangun menggunakan arsitektur modern Android (Jetpack):
•
Bahasa: Java ☕
•
Arsitektur: MVVM (Model-View-ViewModel)
•
Database: Room (SQLite Wrapper)
•
UI: Material Design 3 (Material Components)
•
Reactive UI: LiveData & Observer
•
Library Grafik: MPAndroidChart
•
Build System: Gradle (Groovy/Kotlin DSL)
📁 Struktur Folder Utama
com.example.asistenkeuangan
├── AppDatabase.java          # Inisialisasi Room DB (Singleton)
├── Transaction.java          # Entity/Model Data
├── TransactionDao.java       # Query Database
├── TransactionRepository.java # Manajemen Sumber Data
├── TransactionViewModel.java  # Penghubung UI & Data (Logic)
├── TransactionAdapter.java   # Adapter RecyclerView
├── MainActivity.java         # Halaman Utama (Dashboard)
├── AddTransactionActivity.java # Form Input & Edit (Tabs)
└── SummaryActivity.java      # Halaman Analisis & Grafik
⚙️ Cara Menjalankan Project
1.
Clone atau Download project ini.
2.
Buka menggunakan Android Studio (Ladybug atau terbaru).
3.
Pastikan gambar latar belakang Anda berada di app/src/main/res/drawable/background.jpg.
4.
Lakukan Sync Project with Gradle Files.
5.
Klik Run ke Emulator atau HP Fisik (Min SDK 24 / Android 7.0).
📝 Catatan Pengembangan
Aplikasi ini menggunakan fitur MPAndroidChart untuk grafik. Jika terjadi error pada library, pastikan jitpack.io sudah ditambahkan pada settings.gradle.
Dibuat dengan ❤️ untuk manajemen keuangan yang lebih menyenangkan.
