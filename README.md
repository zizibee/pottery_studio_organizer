# *My Personal Project:* Pottery Studio Organizer 

## Functionality ##
This application would keep track of pottery projects, 
documenting their title/creator, clay type, current clay stage, 
and the next step in production. Projects could be added and
removed to a collection of in-progress pieces that could be 
organized by clay type and status to group projects that can 
be fired together. Users would be able to select a project 
and get a status report listing its current stage and the 
next step to complete. Users could update a project's status 
after finishing a step. Once a project is complete, the piece 
would be added to a collection of completed works. Users could
view the lists of in-progress and completed pieces.
Users could choose whether to save their project collections 
and choose to load these projects from file.

## Users
This program could be used by:
- **Artists** - to keep track and organize their projects
- **Pottery teachers** - to keep track and complete students' 
projects
- **Studio owners** - to keep track of artists' projects and 
organize kiln firings

## Interest
This project is of interest to me because I have loved taking
ceramics courses in school but always wondered how my teachers
could organize and keep track of the many projects students 
were making and what pieces could be fired together. I am 
interested in creating an application that could make this 
process easier and could be of use to individual artists and 
studios too. 

## User Stories
- As a user, I want to be able to add, or remove, a project 
to a list of other in-progress pieces.
- As a user, I want to be able to see a status report for a 
project in my collection.
- As a user, I want to be able to receive a report of which 
projects in my collection can be fired together based on their
clay type and stage.
- As a user, I want to be able to update the status of a project 
and move a finished project to a collection of completed pieces.
- As a user, I want to be able to view my lists of in-progress
and completed projects.
- As a user, I want to have the option of saving my project collections to file.
- As a user, I want to have the option of loading project collections from file.

## Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by clicking  
*Edit* --> *Add a new project*.
- You can generate the second required action related to adding Xs to a Y by clicking
  *Edit* --> *Remove a project*.
- You can locate my visual component by adding a project to the finished project collection
and clicking the project file in the tree menu through *My Projects* 
--> *Finished Projects* --> *(New Project Name)*.
- You can save the state of my application by clicking  
  *File* --> *Save current projects and changes*.
- You can reload the state of my application by clicking  
  *File* --> *Load previously saved projects*.

## Phase 4: Task 2
Mon Apr 10 17:14:53 PDT 2023
\
Project titled "Bowl" added to studio.

Mon Apr 10 17:15:20 PDT 2023
\
Project titled "Bowl" removed from studio.

## Phase 4: Task 3

One part of my design that I would refactor would be the association relationships 
between the StudioAppGUI class and the CeramicProjectList class. When making my UML class diagram,
I noticed that StudioAppGUI has an association with Studio and an association with CeramicProjectList
with a multiplicity of 2 (for the in-progress and finished project collections). However, Studio also
has an association (again with a multiplicity of 2) with CeramicProjectList. So StudioAppGUI accesses
4 CeramicProjectList instances when only 2 are needed. Therefore, I would remove StudioAppGUI's CeramicProjectList
fields and use only the list fields from Studio. This would eliminate code that requires projects to be added to both 
Studio and the CeramicProjectList fields.

Another refactor I would do is altering the CeramicProjectList class itself. After learning about Iterator and
the design pattern that allows classes to use a for-each loop, I realized that it would be helpful for my 
CeramicProjectList class. In the StudioAppGUI class especially, I had to use for loops using an index. Being able to
iterate over CeramicProjectList would have been helpful. Therefore, I would make CeramicProjectList implement
the Iterable interface so that its CeramicProject collection could be iterated over.