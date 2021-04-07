package github.tools.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import github.tools.responseObjects.AddUserToRepoResponse;
import github.tools.responseObjects.CreateRepoResponse;
import github.tools.responseObjects.GetRepoInfoResponse;
import github.tools.responseObjects.UpdateRepoResponse;

public class GitHubApiClient {
    private final String baseUrl = "https://api.github.com";
    private github.tools.client.BasicAuth basicAuth;

    public GitHubApiClient(String user, String token) {
        this.basicAuth = new github.tools.client.BasicAuth(user, token);
    }

    public void setUser(String user) {
        this.basicAuth = new github.tools.client.BasicAuth(user, this.basicAuth.getPassword());
    }

    public void setToken(String token) {
        this.basicAuth = new github.tools.client.BasicAuth(this.basicAuth.getUser(), token);
    }

    // https://docs.github.com/en/rest/reference/repos#create-a-repository-for-the-authenticated-user
    public CreateRepoResponse createRepo(github.tools.client.RequestParams requestParams) {
        String endpoint = String.format("%s/user/repos", baseUrl);
        github.tools.client.Response response = HttpRequest.post(endpoint, requestParams, basicAuth);
        return new CreateRepoResponse((JsonObject) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#get-a-repository
    public GetRepoInfoResponse getRepoInfo(String repoOwner, String repoName) {
        String endpoint = String.format("%s/repos/%s/%s", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
        return new GetRepoInfoResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#update-a-repository
    public UpdateRepoResponse updateRepo(String repoOwner, String repoName, github.tools.client.RequestParams requestParams) {
        String endpoint = String.format("%s/repos/%s/%s", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.patch(endpoint, requestParams, basicAuth);
        return new UpdateRepoResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#delete-a-repository
    public github.tools.responseObjects.DeleteRepoResponse deleteRepo(String repoOwner, String repoName) {
        String endpoint = String.format("%s/repos/%s/%s", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.delete(endpoint, null, basicAuth);
        return new github.tools.responseObjects.DeleteRepoResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#list-repository-contributors
    public github.tools.responseObjects.ListRepoContributorsResponse listRepoContributors(String repoOwner, String repoName, github.tools.client.QueryParams queryParams) {
        String endpoint = String.format("%s/repos/%s/%s/contributors", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, queryParams, basicAuth);
        return new github.tools.responseObjects.ListRepoContributorsResponse((JsonArray) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#list-repositories-for-a-user
    public github.tools.responseObjects.ListReposResponse listRepos(github.tools.client.QueryParams queryParams) {
        String endpoint = String.format("%s/user/repos", baseUrl);
        github.tools.client.Response response = HttpRequest.get(endpoint, queryParams, basicAuth);
        return new github.tools.responseObjects.ListReposResponse((JsonArray) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#list-branches
    public github.tools.responseObjects.ListBranchesInRepoResponse listBranchesInRepo(String repoOwner, String repoName, github.tools.client.QueryParams queryParams) {
        String endpoint = String.format("%s/repos/%s/%s/branches", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, queryParams, basicAuth);
        return new github.tools.responseObjects.ListBranchesInRepoResponse((JsonArray) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#get-a-branch
    public github.tools.responseObjects.GetBranchInfoResponse getBranchInfoFromRepo(String repoOwner, String repoName, String branchName) {
        String endpoint = String.format("%s/repos/%s/%s/branches/%s", baseUrl, repoOwner, repoName, branchName);
        github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
        return new github.tools.responseObjects.GetBranchInfoResponse((JsonObject) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#rename-a-branch
    public github.tools.responseObjects.RenameBranchInRepoResponse renameBranchInRepo(String repoOwner, String repoName, String branchName, github.tools.client.RequestParams requestParams) {
        String endpoint = String.format("%s/repos/%s/%s/branches/%s/rename", baseUrl, repoOwner, repoName, branchName);
        github.tools.client.Response response = HttpRequest.post(endpoint, requestParams, basicAuth);
        return new github.tools.responseObjects.RenameBranchInRepoResponse((JsonObject) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#list-repository-collaborators
    public github.tools.responseObjects.ListRepoCollaboratorsResponse listRepoCollaborators(String repoOwner, String repoName) {
        String endpoint = String.format("%s/repos/%s/%s/collaborators", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
        return new github.tools.responseObjects.ListRepoCollaboratorsResponse((JsonArray) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#check-if-a-user-is-a-repository-collaborator
    public boolean isUserACollaboratorInRepo(String repoOwner, String repoName, String username) {
        try {
            String endpoint = String.format("%s/repos/%s/%s/collaborators/%s", baseUrl, repoOwner, repoName, username);
            github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
            return response.getStatusCode() == 204;
        } catch (github.tools.client.RequestFailedException e) {
            if (e.getStatusCode() == 404) {
                return false;
            }
            throw e;
        }
    }

    // https://docs.github.com/en/rest/reference/repos#add-a-repository-collaborator
    public AddUserToRepoResponse addUserToRepo(String repoOwner, String repoName, String username) {
        String endpoint = String.format("%s/repos/%s/%s/collaborators/%s", baseUrl, repoOwner, repoName, username);
        github.tools.client.Response response = HttpRequest.put(endpoint, null, basicAuth);
        if (response.getStatusCode() == 201) {
            return new AddUserToRepoResponse((JsonObject) response.getBody());
        }
        if (response.getStatusCode() == 204) {
            System.err.println("User is already a collaborator in this repo");
        }
        return null;
    }

    // https://docs.github.com/en/rest/reference/repos#remove-a-repository-collaborator
    public github.tools.responseObjects.RemoveUserFromRepoResponse removeUserFromRepo(String repoOwner, String repoName, String username) {
        String endpoint = String.format("%s/repos/%s/%s/collaborators/%s", baseUrl, repoOwner, repoName, username);
        github.tools.client.Response response = HttpRequest.delete(endpoint, null, basicAuth);
        return new github.tools.responseObjects.RemoveUserFromRepoResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#list-commits
    // note: "sha" is the query param that can be used to specify branch, it's not so obvious
    public github.tools.responseObjects.ListCommitsInRepoResponse listCommitsInRepo(String repoOwner, String repoName, github.tools.client.QueryParams queryParams) {
        String endpoint = String.format("%s/repos/%s/%s/commits", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, queryParams, basicAuth);
        return new github.tools.responseObjects.ListCommitsInRepoResponse((JsonArray)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#get-a-commit
    public github.tools.responseObjects.GetCommitResponse getCommit(String repoOwner, String repoName, String commitHash, github.tools.client.QueryParams queryParams) {
        String endpoint = String.format("%s/repos/%s/%s/commits/%s", baseUrl, repoOwner, repoName, commitHash);
        github.tools.client.Response response = HttpRequest.get(endpoint, queryParams, basicAuth);
        return new github.tools.responseObjects.GetCommitResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/repos#list-repository-languages
    public github.tools.responseObjects.ListRepoLanguagesResponse listRepoLanguages(String repoOwner, String repoName) {
        String endpoint = String.format("%s/repos/%s/%s/languages", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
        return new github.tools.responseObjects.ListRepoLanguagesResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/users#get-a-user
    public github.tools.responseObjects.GetUserResponse getUser(String username) {
        String endpoint = String.format("%s/users/%s", baseUrl, username);
        github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
        return new github.tools.responseObjects.GetUserResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/users#update-the-authenticated-user
    public github.tools.responseObjects.UpdateUserResponse updateUser(github.tools.client.RequestParams requestParams) {
        String endpoint = String.format("%s/user", baseUrl);
        github.tools.client.Response response = HttpRequest.patch(endpoint, requestParams, basicAuth);
        return new github.tools.responseObjects.UpdateUserResponse((JsonObject)response.getBody());
    }

    // https://docs.github.com/en/rest/reference/pulls#list-pull-requests
    public github.tools.responseObjects.ListPullRequestsResponse listPullRequests(String repoOwner, String repoName, github.tools.client.QueryParams queryParams) {
        String endpoint = String.format("%s/repos/%s/%s/pulls", baseUrl, repoOwner, repoName);
        github.tools.client.Response response = HttpRequest.get(endpoint, queryParams, basicAuth);
        return new github.tools.responseObjects.ListPullRequestsResponse((JsonArray) response.getBody());
    }

    // https://docs.github.com/en/rest/reference/pulls#get-a-pull-request
    public github.tools.responseObjects.GetPullRequestResponse getPullRequest(String repoOwner, String repoName, int pullRequestNumber) {
        String endpoint = String.format("%s/repos/%s/%s/pulls/%s", baseUrl, repoOwner, repoName, pullRequestNumber);
        github.tools.client.Response response = HttpRequest.get(endpoint, null, basicAuth);
        return new github.tools.responseObjects.GetPullRequestResponse((JsonObject) response.getBody());
    }
}



