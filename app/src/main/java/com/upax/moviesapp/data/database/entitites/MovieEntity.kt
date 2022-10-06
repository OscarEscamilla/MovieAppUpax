package com.upax.moviesapp.data.database.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.upax.moviesapp.data.model.Movie

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = -1,
    @ColumnInfo(name = "adult") val adult: Boolean = false,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String = "",
    @ColumnInfo(name = "original_language") val original_language: String = "",
    @ColumnInfo(name = "original_title") val original_title: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "popularity") val popularity: Double = -1.0,
    @ColumnInfo(name = "poster_path") val poster_path: String = "",
    @ColumnInfo(name = "release_date") val release_date: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "video") val video: Boolean = false,
    @ColumnInfo(name = "vote_average") val vote_average: Double = -1.0,
    @ColumnInfo(name = "vote_count") val vote_count: Int = -1,
    @ColumnInfo(name = "movie_type") val movie_type: String = ""
)

fun Movie.toMovieEntity(movie_type: String) = MovieEntity(
    id = id,
    adult = adult,
    backdrop_path = backdrop_path,
    original_language = original_language,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    poster_path =  poster_path,
    release_date = release_date,
    title = title,
    video = video,
    vote_average = vote_average,
    vote_count = vote_count,
    movie_type = movie_type
)

fun MovieEntity.toMovie() = Movie(
    id = id,
    adult = adult,
    backdrop_path = backdrop_path,
    original_language = original_language,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    poster_path =  poster_path,
    release_date = release_date,
    title = title,
    video = video,
    vote_average = vote_average,
    vote_count = vote_count,
    movie_type = movie_type
)

fun List<MovieEntity>.toMovieList(): List<Movie>{
    return this.map { it.toMovie() }
}

