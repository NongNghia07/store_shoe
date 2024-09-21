package com.example.store.enums;

public enum OrderStatus {
    PENDING,        // Chờ xử lý
    CONFIRMED,      // Đã xác nhận
    PROCESSING,     // Đang xử lý
    SHIPPED,        // Đã vận chuyển
    DELIVERED,      // Đã giao hàng
    CANCELLED,      // Đã hủy
    RETURNED,        // Đã trả lại
    // chỉ onl
    PAID,           // Đã thanh toán
    PAYMENT_FAILED,  // Thanh toán thất bại
    // chỉ thanh toán khi nhận hàng (cod)
    AWAITING_PAYMENT,   // Chờ thanh toán
    PAYMENT_COLLECTED, // Đã thu tiền
    COD_FAILED,        //Thanh toán khi nhận thất bại
    COMPLETED        // Hoàn thành


    //PENDING,        // Chờ xử lý
    //CONFIRMED,      // Đã xác nhận
    // Ý nghĩa: Đơn hàng đã được xác nhận bởi cửa hàng, thông tin hợp lệ và sẵn sàng để xử lý.
    // Hành động: Bắt đầu quá trình xử lý đơn hàng.

    //PROCESSING,     // Đang xử lý
    // Ý nghĩa: Đơn hàng đang được chuẩn bị, sản phẩm đang được đóng gói và kiểm tra.
    // Hành động: Bắt đầu chuẩn bị hàng hóa, giảm số lượng sản phẩm trong kho (nếu có).

    //SHIPPED,        // Đã vận chuyển
    // Ý nghĩa: Đơn hàng đã được đóng gói và gửi cho đơn vị vận chuyển.
    // Đối với COD, việc thanh toán sẽ thực hiện khi khách hàng nhận hàng.
    // Hành động: Theo dõi trạng thái vận chuyển, chờ khách hàng nhận hàng.

    //DELIVERED,      // Đã giao hàng
    // Ý nghĩa: Đơn hàng đã được giao thành công đến tay khách hàng.
    // Hành động: Kết thúc quá trình vận chuyển, nếu COD, khách hàng đã thanh toán khi nhận hàng.

    //COMPLETED,      // Hoàn thành
    //CANCELLED,      // Đã hủy
    // Ý nghĩa: Đơn hàng đã bị hủy bởi khách hàng hoặc cửa hàng.
    // Hành động: Dừng toàn bộ quá trình xử lý và giao hàng, nếu sản phẩm đã trừ kho thì trả lại kho.

    //RETURNED,        // Đã trả lại
    // Ý nghĩa: Khách hàng đã trả lại hàng sau khi nhận, sản phẩm sẽ được trả về kho.
    // Hành động: Tiến hành quy trình hoàn hàng và hoàn tiền cho khách hàng (nếu thanh toán online), hoặc cập nhật trả hàng cho COD.


    // chỉ onl
    //PAID,           // Đã thanh toán
    // Ý nghĩa: Khách hàng đã hoàn thành quá trình thanh toán trực tuyến thành công.
    // Hành động: Sau khi thanh toán thành công, đơn hàng chuyển sang trạng thái "Confirmed" hoặc "Processing" để tiếp tục xử lý.

    //PAYMENT_FAILED,  // Thanh toán thất bại

    // chỉ thanh toán khi nhận hàng (cod)
    //AWAITING_PAYMENT,
    // Ý nghĩa: Đơn hàng đang chờ thanh toán khi giao hàng đến tay khách hàng
    // Hành động: Đơn vị vận chuyển sẽ thu tiền từ khách hàng khi giao hàng thành công.

    //PAYMENT_COLLECTED, // Đã thu tiền
    // Ý nghĩa: Đơn hàng đã được giao thành công và tiền đã được thu từ khách hàng bởi đơn vị vận chuyển.
    // Hành động: Cập nhật hệ thống là đơn hàng đã hoàn thành thanh toán.

    //COD_FAILED        //Thanh toán khi nhận thất bại
    // Ý nghĩa: Đơn hàng không thể được thanh toán do khách hàng từ chối nhận hàng hoặc không có tiền thanh toán.
    // Hành động: Đơn hàng sẽ bị hủy và sản phẩm có thể được trả về kho. Cửa hàng có thể cần liên hệ với khách hàng để xử lý sự cố.



    // Trong trường hợp có lỗi hoặc hủy: Pending → Cancelled | Confirmed → Failed
    // Một chu kỳ trạng thái thông thường của đơn hàng sẽ có dạng như sau:
    // 1. Pending → 2. Confirmed → 3. Processing → 4. Shipped → 5. Out for Delivery → 6. Delivered → 7. Completed
    // Trong trường hợp trả lại hàng: Delivered → Returned → Refunded
}
