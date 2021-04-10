# GitHub Api Client

## What is this?

This package contains a collection of classes that will allow for easy calls out to the [GitHub API](https://docs.github.com/en).

## Requirements

- Java 8 or higher

## Authentication

For authentication, which GitHub requires in order to use their API, you need to generate a GitHub API token for your account.
Click [here](./access-token) to learn how to generate an access token.

## Usage

A GitHubApiClient object instance can be created like so, where `user` is your GitHub username and `token` is your GitHub API access token:

```java
GitHubApiClient gitHubApiClient = new GitHubApiClient(user, token);
```

From here, you can call any of the available methods to get a response from GitHub with the desired info.
For example, if you wanted to get info about a repo, you would call the `getRepoInfo` method.
The below will return info for this repo:

```java
GitHubApiClient gitHubApiClient = new GitHubApiClient(user, token);
GetRepoInfoResponse repoInfo = gitHubApiClient.getRepoInfo("CSC109", "GitHubApiClient");
```

Required imports:

```java


```

Click [here](./methods-overview) for descriptions/examples for each GitHub Api Client method.



