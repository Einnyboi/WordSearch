# PROYEK UAS

Mata Kuliah : Data Structures
Dosen : Jeanny Pragantha

# KELOMPOK 6
Anggota :
1. Vanesa Yolanda - 535240071
2. Cathrine Sandrina - 535240075
3. Jessica Perez Chen - 535240188

# Pendahuluan
WordSearch adalah sebuah aplikasi Java yang digunakan untuk mencari
kata kunci dalam sebuah teks. Aplikasi ini menggunakan sebuah bentuk
struktur data yang umum digunakan yaitu Trie.

Trie adalah sebuah struktur data yang digunakan untuk menyimpan
kata-kata dalam sebuah kamus. Trie dapat digunakan untuk mencari
kata-kata dalam sebuah teks dengan cepat.

Aplikasi ini menampilkan UI simpel untuk memudahkan proses pengunggahan
file teks dan penggunaan aplikasi kepada user. Setelah file diunggah, user
dapat mencari kata kunci yang bersifat satu kata dan bukan frasa. Jika
kata berhasil ditemukan, maka kata akan di highlight dan akan dihitung jumlah
kata yang ditemukan.

# WordSearch Java Swing App

Aplikasi desktop Java untuk mencari, menyorot, dan menavigasi kata pada dokumen PDF dan DOCX. Mendukung upload file, highlight, navigasi hasil, dan UI modern.

## Cara Menjalankan di VS Code (Maven)

### 1. Buka Folder Project di VS Code
- Pilih menu **File > Open Folder...**
- Arahkan ke folder project ini (`WordSearch`)

### 2. Pastikan Sudah Install Java & Maven
- Java JDK 11+ (cek: `java -version` di terminal)
- Maven (cek: `mvn -version` di terminal)
- Jika belum, install dari https://adoptium.net/ dan https://maven.apache.org/

### 3. Build Project
Buka terminal di VS Code (Ctrl+`), lalu jalankan:
```powershell
mvn clean package
```
- Ini akan meng-compile kode dan menghasilkan file JAR di folder `target/`

### 4. Run Aplikasi dari Maven
Masih di terminal, jalankan:
```powershell
mvn exec:java -Dexec.mainClass="com.eyin.wordsearch.App"
```
- Atau gunakan fitur **Run** di VS Code (ikon play di pojok atas editor pada file `App.java`)

### 5. Alternatif: Run JAR Langsung
Setelah build, bisa juga jalankan:
```powershell
java -jar target/WordSearch-1.0-SNAPSHOT.jar
```

## Fitur Utama
- Upload file PDF/DOCX
- Cari kata (case-insensitive, whole word)
- Highlight & navigasi hasil (Next/Prev)
- UI modern, responsif, dan mudah digunakan

## Catatan
- Semua dependensi (PDFBox, Apache POI) otomatis di-handle Maven.
- Untuk development, **disarankan run via Maven** agar dependency selalu up-to-date.
- Jika ada error, pastikan file yang diupload adalah PDF/DOCX dan Java/Maven sudah terinstall.

---

**Selamat mencoba!**

- Jurusan Teknik Informatika
- Fakultas Teknologi Informasi
- Universitas Tarumanagara
