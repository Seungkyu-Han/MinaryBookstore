package np.minarybook.model.dto.book.res

import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes

data class BookGetSaveRes(
    val bookForSaleGetResList: List<BookForSaleGetRes>,
    val bookForRentGetResList: List<BookForRentGetRes>
)
