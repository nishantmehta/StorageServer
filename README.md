# StorageServer
Backend to be used by image/blob storage applications

# Concepts for this project
## Blob
A peice of media object, can be video/image/audio/gif/etc. Each one of the media object will have metadata associated with it like the following:
- Mime type
- internal object type
- created date
- updated date
- user history
- storage source
- internal ID
- external ID

This metadata will evolve over time and should and design should save space for expansion. 


## Entity
Any person/company/group which intendes to store/lookup a blob on this platform is a entity. It is required that we difine further sub catogery as needed. But the design should be as generic as possible.


## Bucket
A storage space defined by/for a entity which can be shared by other entities in the system. 


# Phases of software development
