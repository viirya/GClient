
# The Android client for grand challenge project

This is an Android client for my grand challenge project of ACMM 2009. The project returns image search results as semantically/visually related groups with descriptive tags. The image groups are generated based on pre-computed image clustering in offline stage. In order to cluster large-scale images, the project implements MapReduce version affinity propagation algorithm on Hadoop platform. The user interface of the system includes a web-based image search system and this Android client. This Android client shows the benefit of grouping image search results. Compared with list-based representation, this group-based way is more fitting for the limited screen of mobile devices. Since it is not a public service now, even this codes can be compiled, you can not use it. For demostraction, I record a video:

[Demo video](http://www.viirya.org/demo/demo_web_and_mobile.mp4 "System demo video")

This client is written by Scala and managed by [sbt](http://code.google.com/p/simple-build-tool/) tool. It is used also to demostrate how to write Android client using Scala language.


