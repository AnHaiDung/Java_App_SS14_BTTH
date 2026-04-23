1. Lỗi LazyInitializationException xảy ra khi: bạn cố gắng truy cập vào một tập hợp dữ liệu (Collection) được cấu hình là FetchType.LAZY (như danh sách histories) sau khi Session đã bị đóng. Lúc này, Hibernate không còn kết nối với Database để tải nốt phần dữ liệu còn thiếu.

2. Cách sửa với JOIN FETCH trong HQL: dùng HQL JOIN FETCH