package com.example.movieapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName


/*data class Results (

    @SerializedName("adult"             ) var adult            : Boolean?       = null,
    @SerializedName("backdrop_path"     ) var backdropPath     : String?        = null,
    @SerializedName("genre_ids"         ) var genreIds         : ArrayList<Int> = arrayListOf(),
    @SerializedName("id"                ) var id               : Int?           = null,
    @SerializedName("original_language" ) var originalLanguage : String?        = null,
    @SerializedName("original_title"    ) var originalTitle    : String?        = null,
    @SerializedName("overview"          ) var overview         : String?        = null,
    @SerializedName("popularity"        ) var popularity       : Double?        = null,
    @SerializedName("poster_path"       ) var posterPath       : String?        = null,
    @SerializedName("release_date"      ) var releaseDate      : String?        = null,
    @SerializedName("title"             ) var title            : String?        = null,
    @SerializedName("video"             ) var video            : Boolean?       = null,
    @SerializedName("vote_average"      ) var voteAverage      : Double?        = null,
    @SerializedName("vote_count"        ) var voteCount        : Int?           = null
)

data class Dates (

    @SerializedName("maximum" ) var maximum : String? = null,
    @SerializedName("minimum" ) var minimum : String? = null

)

data class ExampleJson2KtKotlin (

    @SerializedName("dates"         ) var dates        : Dates?             = Dates(),
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null

)

*/
@Parcelize
data class Movie(
    val id: Int,
    val title:String = "",
    val overview: String = "",
    val poster_path: String = "",
    val release_date: String = "",
    val page: Int,
    val vote_average: Double
): Parcelable

data class MovieResponse(
    val results: List<Movie>
)

/*
@Parcelize
data class Category(val idCategory:String = "",
                    val strCategory:String = "",
                    val strCategoryThumb:String = "",
                    val strCategoryDescription:String = ""
):Parcelable
data class CategoriesResponse(val categories: List<Category>)
 */