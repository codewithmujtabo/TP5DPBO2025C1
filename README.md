# Database Mahasiswa

## Deskripsi Program
Program ini merupakan aplikasi berbasis Java yang terhubung ke database MySQL untuk mengelola data mahasiswa. Program ini menyediakan fitur untuk membaca (SELECT), menambah (INSERT), memperbarui (UPDATE), dan menghapus (DELETE) data mahasiswa dari database.

## Desain Program
Program ini menggunakan JDBC (Java Database Connectivity) untuk berkomunikasi dengan MySQL. Berikut adalah komponen utama dalam program ini:

1. **`Database.java`**
    - Mengatur koneksi ke database.
    - Menyediakan metode untuk menjalankan perintah SQL (SELECT, INSERT, UPDATE, DELETE).

2. **`Menu.java`**
    - Menyediakan antarmuka untuk pengguna.
    - Memanggil fungsi dari `Database.java` untuk berinteraksi dengan database.

## Alur Program
1. Program menginisialisasi koneksi ke database `db_mahasiswa`.
2. Pengguna dapat memilih untuk:
    - Menampilkan data mahasiswa.
    - Menambahkan mahasiswa baru.
    - Memperbarui data mahasiswa.
    - Menghapus mahasiswa.
3. Program akan menjalankan query sesuai dengan pilihan pengguna.
4. Jika ada kesalahan koneksi, program akan menampilkan pesan error.

## Hasil
[Screen Recording 2025-03-30 at 20.58.11.mov](Screen%20Recording%202025-03-30%20at%2020.58.11.mov)
