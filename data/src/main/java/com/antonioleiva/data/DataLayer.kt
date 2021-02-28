package com.antonioleiva.data

class DataLayer {
    /*
All data needed for the application comes from this layer through a UserRepository implementation
(the interface is in the domain layer) that uses a Repository Pattern with a strategy that,
through a factory, picks different data sources depending on certain conditions.

For instance, when getting a user by id, the disk cache data source will be selected
if the user already exists in cache, otherwise the cloud will be queried to retrieve
the data and later save it to the disk cache.

The idea behind all this is that the origin of the data is transparent to the client,
which does not care if the data is coming from memory, disk or the cloud,
the only truth is that the information will arrive and will be gotten.
This layer is a pure java module without any android dependencies.
All the external components use interfaces when connecting to the business objects.
     */
}