# Tìm kiếm và phân loại văn bản (HashMap + TreeMap)

Mô tả: chương trình đọc nhiều file `.txt`, đếm số lần xuất hiện của từng từ, in ra các từ phổ biến nhất và cho phép xem theo thứ tự chữ cái (TreeMap).

Yêu cầu: Java 8+ (dùng `javac`/`java`).

Files:
- `src/Main.java` — CLI runner
- `src/TextProcessor.java` — tokenizer
- `src/WordCounter.java` — đếm từ bằng `HashMap` và trả về top-k
- `sample_texts/*.txt` — ví dụ dữ liệu đầu vào

Compile & run (PowerShell):

```powershell
javac -d out src\*.java
java -cp out Main sample_texts
```

Options:
- Pass one or more files or a directory containing `.txt` files.
- Use `-k=N` to set how many top words to show (default 20). Example:

```powershell
java -cp out Main -k=10 sample_texts\sample1.txt sample_texts\sample2.txt
```

Gợi ý cải tiến:
- Loại bỏ stopwords (từ dừng) cho ngôn ngữ cụ thể
- Hỗ trợ song song (multithreading) cho nhiều file lớn
- Xuất ra CSV/JSON để phân tích tiếp
